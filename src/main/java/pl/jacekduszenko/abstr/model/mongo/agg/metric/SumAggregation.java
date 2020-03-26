package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import org.bson.conversions.Bson;

import java.util.Map;

public class SumAggregation extends BaseMongoAggregation {

    public SumAggregation(Map<String, Object> rawDataChunk) {
        super(rawDataChunk);
    }

    @Override
    public Bson convertToLanguageSpecific() {
        return null;
    }
}
