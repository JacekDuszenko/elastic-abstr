package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import org.bson.conversions.Bson;
import org.elasticsearch.search.aggregations.AggregationExecutionException;

import java.util.Map;

public class StringStatsAggregation extends BaseMongoMetricAggregation {

    public StringStatsAggregation(Map<String, Object> rawDataChunk) {
        super(rawDataChunk);
    }

    @Override
    public Bson convertToLanguageSpecific() throws AggregationExecutionException {
        return null; //TODO
    }
}
