package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.conversions.Bson;
import org.elasticsearch.search.aggregations.AggregationExecutionException;

import java.util.Map;

public class AvgAggregation extends BaseMongoMetricAggregation {

    private static final String AVERAGE_FIELD_KEY = "_avg";

    public AvgAggregation(Map<String, Object> rawDataChunk) {
        super(rawDataChunk);
    }

    @Override
    public Bson convertToLanguageSpecific() throws AggregationExecutionException {
        String field = getFieldValueFromMap();
        return Aggregates.group(groupById(), Accumulators.avg(AVERAGE_FIELD_KEY, prependDollar(field)));
    }
}
