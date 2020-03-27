package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.conversions.Bson;
import org.elasticsearch.search.aggregations.AggregationExecutionException;

import java.util.Map;

public class MaxAggregation extends BaseMongoMetricAggregation {

    private final static String MAX_KEYWORD = "_max";

    public MaxAggregation(Map<String, Object> rawDataChunk) {
        super(rawDataChunk);
    }


    @Override
    public Bson convertToLanguageSpecific() throws AggregationExecutionException {
        String field = getFieldValueFromMap();
        return Aggregates.group(groupById(), Accumulators.max(MAX_KEYWORD, prependDollar(field)));
    }
}
