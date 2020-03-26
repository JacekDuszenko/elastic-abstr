package pl.jacekduszenko.abstr.model.mongo.agg;

import org.bson.conversions.Bson;

public class EmptyAggregation implements MongoAggregation {
    @Override
    public Bson convertToLanguageSpecific() {
        return null;
    }
}
