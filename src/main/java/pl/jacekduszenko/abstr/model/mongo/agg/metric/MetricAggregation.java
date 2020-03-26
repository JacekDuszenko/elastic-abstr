package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import org.bson.conversions.Bson;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;

public abstract class MetricAggregation implements MongoAggregation {
    @Override
    public Bson convertToLanguageSpecific() {
        return null;
    }
}
