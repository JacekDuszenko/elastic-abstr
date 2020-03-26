package pl.jacekduszenko.abstr.model.mongo.agg;

import org.bson.conversions.Bson;
import pl.jacekduszenko.abstr.model.Aggregation;

public interface MongoAggregation extends Aggregation<Bson> {
}
