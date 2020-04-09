package pl.jacekduszenko.abstr.model.mongo.agg.bucket.strategy.impl;

import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SingleNestedAggregationStrategy implements MongoBucketAggregationStrategy {
    @Override
    public Document composeAggregation(Map<String, Object> rawQuery) {
        return null;
    }

    @Override
    public boolean applicable(int numberOfNestedAggregations) {
        return numberOfNestedAggregations == 1;
    }
}
