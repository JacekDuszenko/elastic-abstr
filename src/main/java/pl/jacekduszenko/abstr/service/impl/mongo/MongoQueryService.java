package pl.jacekduszenko.abstr.service.impl.mongo;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.bson.Document;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;
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

    @SneakyThrows
    public List search(String elasticQuery, String collection, Boolean verbose) {
        org.springframework.data.mongodb.core.query.Query q = obtainMongoQuery(elasticQuery);
        Stream<Document> verboseResults = getSchemalessResultStream(collection, q);
        return verbose ? asList(verboseResults) : asList(filterMongoSpecificFields(verboseResults));
    }

    private org.springframework.data.mongodb.core.query.Query obtainMongoQuery(String elasticQuery) throws VisitorCreationException, TranslationException {
        Query luceneQuery = translateEsToLucene(elasticQuery);
        return translateLuceneToMongo(luceneQuery);
    }

    private Query translateEsToLucene(String elasticQuery) {
        return indexQueryParserService.parse(elasticQuery).query();
    }

    private org.springframework.data.mongodb.core.query.Query translateLuceneToMongo(Query luceneQuery) throws VisitorCreationException, TranslationException {
        return luceneToMongoTranslator.translateFromLuceneQuery(luceneQuery);
    }

    private Stream<Document> filterMongoSpecificFields(Stream<Document> verboseResults) {
        return verboseResults.peek(doc -> mongoSpecificFields.forEach(doc::remove));
    }

    private Stream<Document> getSchemalessResultStream(String collection, org.springframework.data.mongodb.core.query.Query q) {
        Iterable<Document> results = () -> mongoTemplate.getCollection(collection).find(q.getQueryObject()).cursor();
        return StreamSupport.stream(results.spliterator(), false);
    }

    private List<Document> asList(Stream<Document> s) {
        return s.collect(Collectors.toList());
    }
}
