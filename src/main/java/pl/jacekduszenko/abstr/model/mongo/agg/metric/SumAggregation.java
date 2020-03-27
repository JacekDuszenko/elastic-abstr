package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.conversions.Bson;
import org.elasticsearch.search.aggregations.AggregationExecutionException;

import java.util.Map;

public class SumAggregation extends BaseMongoMetricAggregation {

    private static final String SUM_FIELD_KEY = "_sum";

    public SumAggregation(Map<String, Object> rawDataChunk) {
        super(rawDataChunk);
    }

    @Override
    public Bson convertToLanguageSpecific() throws AggregationExecutionException {
        String field = getFieldValueFromMap();
        return Aggregates.group(groupById(), Accumulators.sum(SUM_FIELD_KEY, prependDollar(field)));
    }
}
