package pl.jacekduszenko.abstr.model.mongo.agg;

import org.bson.conversions.Bson;

public class BucketAggregation implements MongoAggregation {
    @Override
    public Bson convertToLanguageSpecific() {
        return null;
    }
}
