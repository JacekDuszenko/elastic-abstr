package pl.jacekduszenko.abstr.service.impl;

import org.elasticsearch.index.query.IndexQueryParserService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jacekduszenko.abstr.data.ElasticsearchQueryLoader;
import pl.jacekduszenko.abstr.service.QueryService;

import java.io.IOException;

import static org.mockito.Mockito.mock;


public class MongoQueryServiceTest {

    private QueryService queryService;
    private IndexQueryParserService indexQueryParserService = mock(IndexQueryParserService.class);

    @BeforeEach
    void setUp() {
        queryService = new MongoQueryService(indexQueryParserService);
    }

    @Test
    @Ignore
    void shouldTranslateQueryAndReturnValidResultsFromMongo() throws IOException {
        String validElasticsearchQuery = ElasticsearchQueryLoader.loadElasticQueryFromFile("boosting_query.json");
        queryService.search(validElasticsearchQuery);
        //TODO implement rest of the test after custom lucene parser implemented
    }
}