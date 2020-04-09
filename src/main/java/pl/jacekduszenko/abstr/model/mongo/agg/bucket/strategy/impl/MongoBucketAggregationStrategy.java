package pl.jacekduszenko.abstr.model.mongo.agg.bucket.strategy.impl;

import org.bson.Document;

import java.util.Map;

public interface MongoBucketAggregationStrategy {
    Document composeAggregation(Map<String, Object> rawQuery);
    boolean applicable(int numberOfNestedAggregations);
}
