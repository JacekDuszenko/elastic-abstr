package pl.jacekduszenko.abstr.service.impl.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jacekduszenko.abstr.data.ElasticsearchQueryProvider;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;
import pl.jacekduszenko.abstr.model.mongo.agg.metric.AvgAggregation;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class MongoMongoAggregationsObjectMapperTest {

    private final String AVG_QUERY_FILENAME = "aggregation_match_query.json";
    private MongoAggregationsObjectMapper mongoAggregationsObjectMapper;

    @BeforeEach
    void beforeAll() {
        mongoAggregationsObjectMapper = new MongoAggregationsObjectMapper(new ObjectMapper(), new MongoAggregationsFactory());
    }

    @Test
    void shouldMapAvgQuery() {
        //given
        String query = ElasticsearchQueryProvider.loadElasticQueryFromFile(AVG_QUERY_FILENAME);

        //when
        List<MongoAggregation> aggregations = mongoAggregationsObjectMapper.mapQueryStringToAggregations(query);

        //then
        assertThat(aggregations.size(), is(1));
        MongoAggregation averageAggregation = aggregations.get(0);
        assertThat(aggregations.getClass(), is(AvgAggregation.class));
    }
}