package pl.jacekduszenko.abstr.model;

import org.bson.conversions.Bson;

public class EmptyQuery implements MongoAggregationQuery {
    @Override
    public Bson asBson() {
        return null;
    }
}
