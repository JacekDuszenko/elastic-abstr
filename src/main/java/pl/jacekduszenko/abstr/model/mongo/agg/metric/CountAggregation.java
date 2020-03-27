package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.conversions.Bson;
import org.elasticsearch.search.aggregations.AggregationExecutionException;

import java.util.Map;

public class CountAggregation extends BaseMongoMetricAggregation {

    private final static String COUNT_KEYWORD = "_count";

    public CountAggregation(Map<String, Object> rawDataChunk) {
        super(rawDataChunk);
    }

    @Override
    public Bson convertToLanguageSpecific() throws AggregationExecutionException {
        return Aggregates.group(groupById(), Accumulators.sum(COUNT_KEYWORD, 1));
    }
}
