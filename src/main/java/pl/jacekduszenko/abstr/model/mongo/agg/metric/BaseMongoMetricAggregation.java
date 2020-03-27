package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import lombok.RequiredArgsConstructor;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;

import java.util.Map;

import static ch.qos.logback.core.CoreConstants.DOLLAR;

@RequiredArgsConstructor
public abstract class BaseMongoMetricAggregation implements MongoAggregation {

    protected static final String FIELD_KEY = "field";

    private final Map<String, Object> rawDataChunk;

    protected String prependDollar(String field) {
        return DOLLAR + field;
    }

    protected String groupById() {
        return "_id";
    }

    protected String getFieldValueFromMap() {
        return (String) rawDataChunk.get(FIELD_KEY);
    }
}
