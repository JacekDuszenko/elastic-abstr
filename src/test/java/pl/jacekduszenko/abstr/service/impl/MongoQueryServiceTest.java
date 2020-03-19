package pl.jacekduszenko.abstr.service.impl;

import org.elasticsearch.index.query.IndexQueryParserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jacekduszenko.abstr.service.QueryService;

import static org.mockito.Mockito.mock;


public class MongoQueryServiceTest {
    private static final String exampleQuery = "\"{\\n\" +\n" +
            "        \"  \\\"query\\\": {\\n\" +\n" +
            "        \"    \\\"match\\\" : {\\n\" +\n" +
            "        \"      \\\"name\\\" : \\\"some string\\\"\\n\" +\n" +
            "        \"    }\\n\" +\n" +
            "        \"  }\\n\" +\n" +
            "        \"}\"";

    private QueryService queryService;

    private IndexQueryParserService indexQueryParserService = mock(IndexQueryParserService.class);

    @BeforeEach
    void setUp() {
        queryService = new MongoQueryService(indexQueryParserService);
    }

    @Test
    void shouldParseQueryToLuceneDsl() {

    }
}