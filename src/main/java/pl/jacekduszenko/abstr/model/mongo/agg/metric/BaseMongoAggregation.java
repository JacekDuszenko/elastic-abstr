package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import lombok.RequiredArgsConstructor;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;

import java.util.Map;

@RequiredArgsConstructor
public abstract class BaseMongoAggregation implements MongoAggregation {
    protected final Map<String, Object> rawDataChunk;
}
