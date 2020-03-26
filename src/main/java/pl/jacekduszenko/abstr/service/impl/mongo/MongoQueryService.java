package pl.jacekduszenko.abstr.service.impl.mongo;

import com.mongodb.client.model.Aggregates;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;
import pl.jacekduszenko.abstr.service.QueryPartExtractor;
import pl.jacekduszenko.abstr.service.QueryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    @SneakyThrows
    public List search(String elasticQuery, String collection, Boolean verbose) {
        List<Bson> queryConditions = createQueryConditions(elasticQuery);
        Stream<Document> verboseResults = getSchemalessResultStream(collection, queryConditions);
        return verbose ? asList(verboseResults) : asList(filterMongoSpecificFields(verboseResults));
    }

    private List<Bson> createQueryConditions(String elasticQuery) throws VisitorCreationException, TranslationException {
        Tuple2<String, String> matchAndAggregation = extractor.extractMatchAndAggregatePartFromQuery(elasticQuery);
        org.springframework.data.mongodb.core.query.Query matchQuery = obtainMatchQuery(matchAndAggregation._1);
        List<Bson> conditions = obtainAggregations(matchAndAggregation._2);
        conditions.add(Aggregates.match(matchQuery.getQueryObject()));
        return conditions;
    }

    private List<Bson> obtainAggregations(String s) {
        return mongoAggregationFactory.fromQueryString(s);
    }

    private org.springframework.data.mongodb.core.query.Query obtainMatchQuery(String matchQuery) throws VisitorCreationException, TranslationException {
        Query luceneMatchQuery = translateElasticsearchQueryToLucene(matchQuery);
        return translateLuceneToMongo(luceneMatchQuery);
    }

    private Query translateElasticsearchQueryToLucene(String elasticQuery) {
        return indexQueryParserService.parse(elasticQuery).query();
    }

    private org.springframework.data.mongodb.core.query.Query translateLuceneToMongo(Query luceneQuery) throws VisitorCreationException, TranslationException {
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
