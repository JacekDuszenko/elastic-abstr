package pl.jacekduszenko.abstr.service.impl.mongo;


import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jacekduszenko.abstr.model.exception.MongoAggregationCreationException;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;
import pl.jacekduszenko.abstr.model.mongo.agg.bucket.MongoTermBucketAggregation;
import pl.jacekduszenko.abstr.model.mongo.agg.metric.*;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class MongoAggregationModelFactoryTest {

    private MongoAggregationModelFactory mongoAggregationModelFactory;

    @BeforeEach
    void setUp() {
        mongoAggregationModelFactory = new MongoAggregationModelFactory();
    }

    @Test
    @SneakyThrows
    void shouldCreateValidMongoPlainAggregations() {
        createAggregationFromKeywordAndAssertClass("avg", AvgAggregation.class);
        createAggregationFromKeywordAndAssertClass("cardinality", CountAggregation.class);
        createAggregationFromKeywordAndAssertClass("stats", StatsAggregation.class);
        createAggregationFromKeywordAndAssertClass("min", MinAggregation.class);
        createAggregationFromKeywordAndAssertClass("max", MaxAggregation.class);
        createAggregationFromKeywordAndAssertClass("sum", SumAggregation.class);
    }

    @Test
    @SneakyThrows
    void shouldCreateValidMongoBucketAggregations() {
        createAggregationFromKeywordAndAssertClass("terms", MongoTermBucketAggregation.class);

    }

    private void createAggregationFromKeywordAndAssertClass(String aggName, Class<? extends MongoAggregation> assertedClass) throws MongoAggregationCreationException {
        final Map<String, Object> queryAsMap = Map.of();
        MongoAggregation agg = mongoAggregationModelFactory.createFromKeyword(aggName, queryAsMap);
        assertThat(agg.getClass(), is(assertedClass));
    }
}