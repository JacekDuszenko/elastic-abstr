package pl.jacekduszenko.abstr.service.impl.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MongoAggregationsObjectMapperTest {

    private MongoAggregationsObjectMapper mongoAggregationsObjectMapper;

    @BeforeEach
    void beforeAll() {
        mongoAggregationsObjectMapper = new MongoAggregationsObjectMapper(new ObjectMapper());
    }

    @Test
    void nowyTest() {

    }
}