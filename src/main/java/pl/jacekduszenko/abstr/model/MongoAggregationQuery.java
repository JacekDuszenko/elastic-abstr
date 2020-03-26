package pl.jacekduszenko.abstr.model;

import org.bson.conversions.Bson;

public interface MongoAggregationQuery {

    Bson asBson();
}
