package pl.jacekduszenko.abstr.service.impl.mongo;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.MongoEntityMapping;
import pl.jacekduszenko.abstr.service.QueryService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoQueryService implements QueryService {

    private final IndexQueryParserService indexQueryParserService;
    private final LuceneToMongoTranslator luceneToMongoTranslator;
    private final MongoTemplate mongoTemplate;

    @SneakyThrows
    public List search(String elasticQuery, String collection) {
        Query luceneQuery = indexQueryParserService.parse(elasticQuery).query();
        org.springframework.data.mongodb.core.query.Query q = luceneToMongoTranslator.translateFromLuceneQuery(luceneQuery);
        return mongoTemplate.find(q, findEntityClassByCollectionName(collection));
    }

    private Class<?> findEntityClassByCollectionName(String collection) {
        return MongoEntityMapping.mapping.get(collection);
    }
}
