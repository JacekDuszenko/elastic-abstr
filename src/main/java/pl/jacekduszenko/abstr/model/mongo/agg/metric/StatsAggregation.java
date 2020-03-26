package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import org.bson.conversions.Bson;

import java.util.Map;

public class StatsAggregation extends BaseMongoAggregation {

    public StatsAggregation(Map<String, Object> rawDataChunk) {
        super(rawDataChunk);
    }

    @Override
    public Bson convertToLanguageSpecific() {
        return null;
    }
}
