package pl.jacekduszenko.abstr.model.mongo.agg.bucket.strategy.impl;

import net.minidev.json.JSONObject;
import org.bson.conversions.Bson;

import java.util.Map;

import static ch.qos.logback.core.CoreConstants.DOLLAR;
import static pl.jacekduszenko.abstr.model.mongo.agg.bucket.strategy.impl.ZeroNestedAggregationStrategy.FIELD_KEY;

public interface MongoBucketAggregationStrategy {
    Bson composeAggregation(Map<String, Object> rawQuery);

    boolean applicable(int numberOfNestedAggregations);

    default String prependDollar(String word) {
        return DOLLAR + word;
    }

    default String getGroupByFieldName(Map<String, Object> rawQuery) {
        return new JSONObject(rawQuery).getAsString(FIELD_KEY);
    }
}
