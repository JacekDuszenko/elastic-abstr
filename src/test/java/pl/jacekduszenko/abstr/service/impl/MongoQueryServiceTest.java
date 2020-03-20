package pl.jacekduszenko.abstr.service.impl;

import org.elasticsearch.index.query.IndexQueryParserService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jacekduszenko.abstr.data.ElasticsearchQueryProvider;
import pl.jacekduszenko.abstr.service.QueryService;
import pl.jacekduszenko.abstr.service.impl.mongo.LuceneToMongoTranslator;
import pl.jacekduszenko.abstr.service.impl.mongo.MongoQueryService;

import static org.mockito.Mockito.mock;


public class MongoQueryServiceTest {

    private QueryService queryService;
    private IndexQueryParserService indexQueryParserService = mock(IndexQueryParserService.class);
    private LuceneToMongoTranslator luceneToMongoTranslator = mock(LuceneToMongoTranslator.class);

    @BeforeEach
    void setUp() {
        queryService = new MongoQueryService(indexQueryParserService, luceneToMongoTranslator, null);
    }

    @Test
    @Ignore
    void shouldTranslateQueryAndReturnValidResultsFromMongo() throws Exception {
        String validElasticsearchQuery = ElasticsearchQueryProvider.loadElasticQueryFromFile("boosting_query.json");
        queryService.search(validElasticsearchQuery,"mongoMockData");
        //TODO implement rest of the test after custom lucene parser implemented
    }
}