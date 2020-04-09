package pl.jacekduszenko.abstr.service.impl.mongo;

import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.query.QueryParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;
import pl.jacekduszenko.abstr.service.QueryPartExtractor;
import pl.jacekduszenko.abstr.service.QueryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Aggregates.match;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static pl.jacekduszenko.abstr.integration.mongo.MongoSpecificFields.mongoSpecificFields;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoQueryService implements QueryService {

    private final IndexQueryParserService indexQueryParserService;
    private final LuceneToMongoTranslator luceneToMongoTranslator;
    private final MongoTemplate mongoTemplate;
    private final QueryPartExtractor<String, String> extractor;
    private final MongoAggregationFactory mongoAggregationFactory;
    private final static int FIRST_INDEX = 0;

    @SneakyThrows
    public List search(String elasticQuery, String collection, Boolean verbose) {
        List<Bson> queryConditions = createQueryConditions(elasticQuery);
        Stream<Document> verboseResults = getSchemalessResultStream(collection, queryConditions);
        return verbose ? asList(verboseResults) : asList(filterMongoSpecificFields(verboseResults));
    }

    private List<Bson> createQueryConditions(String elasticQuery) throws VisitorCreationException, TranslationException {
        Tuple2<String, String> matchAndAggregation = extractor.extractMatchAndAggregatePartFromQuery(elasticQuery);
        Optional<org.springframework.data.mongodb.core.query.Query> matchQuery = obtainMatchQuery(matchAndAggregation._1);
        List<Bson> conditions = obtainAggregations(matchAndAggregation._2);
        prependMatchQueryToAggregations(matchQuery, conditions);
        return conditions;
    }

    private void prependMatchQueryToAggregations(Optional<org.springframework.data.mongodb.core.query.Query> matchQuery, List<Bson> conditions) {
        matchQuery.ifPresent((query) -> conditions.add(FIRST_INDEX, match(query.getQueryObject())));
    }

    private List<Bson> obtainAggregations(String s) {
        return mongoAggregationFactory.fromAggregationString(s);
    }

    private Optional<org.springframework.data.mongodb.core.query.Query> obtainMatchQuery(String matchQuery) {
        Optional<Query> luceneMatchQuery = translateElasticsearchQueryToLucene(matchQuery);
        return luceneMatchQuery.map(this::translateLuceneToMongo);
    }

    private Optional<Query> translateElasticsearchQueryToLucene(String elasticQuery) {
        try {
            return ofNullable(indexQueryParserService.parse(elasticQuery).query());
        } catch (QueryParsingException ex) {
            return empty();
        }
    }

    @SneakyThrows
    private org.springframework.data.mongodb.core.query.Query translateLuceneToMongo(Query luceneQuery) {
        return luceneToMongoTranslator.translateFromLuceneQuery(luceneQuery);
    }

    private Stream<Document> filterMongoSpecificFields(Stream<Document> verboseResults) {
        return verboseResults.peek(doc -> mongoSpecificFields.forEach(doc::remove));
    }

    private Stream<Document> getSchemalessResultStream(String collection, List<Bson> conditions) {
        Iterable<Document> results = () -> mongoTemplate.getCollection(collection).aggregate(conditions).iterator();
        return StreamSupport.stream(results.spliterator(), false);
    }

    private List<Document> asList(Stream<Document> s) {
        return s.collect(Collectors.toList());
    }
}
