package pl.jacekduszenko.abstr.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jacekduszenko.abstr.service.QueryService;


public class MongoQueryServiceTest {
    private static final String exampleQuery = "\"{\\n\" +\n" +
            "        \"  \\\"query\\\": {\\n\" +\n" +
            "        \"    \\\"match\\\" : {\\n\" +\n" +
            "        \"      \\\"name\\\" : \\\"some string\\\"\\n\" +\n" +
            "        \"    }\\n\" +\n" +
            "        \"  }\\n\" +\n" +
            "        \"}\"";


    private QueryService queryService;

    @BeforeEach
    void setUp() {
        queryService = new MongoQueryService();
    }

    @Test
    void shouldParseQueryToLuceneDsl() {

    }
}