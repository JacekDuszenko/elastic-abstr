package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import org.bson.conversions.Bson;

import java.util.Map;

public class MinAggregation extends BaseMongoAggregation {
    public MinAggregation(Map<String, Object> rawDataChunk) {
        super(rawDataChunk);
    }

    @Override
    public Bson convertToLanguageSpecific() {
        return null;
    }
}
