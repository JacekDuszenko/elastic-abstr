package pl.jacekduszenko.abstr.model.mongo.agg.bucket.strategy.impl;

import org.bson.Document;

import java.util.Map;

public class ZeroNestedAggregationStrategy implements MongoBucketAggregationStrategy {
    @Override
    public Document composeAggregation(Map<String, Object> rawQuery) {
        return null;
    }

    @Override
    public boolean applicable(int numberOfNestedAggregations) {
        return numberOfNestedAggregations == 0;
    }
}
