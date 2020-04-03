package pl.jacekduszenko.abstr.model.mongo.agg.bucket;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import org.elasticsearch.search.aggregations.AggregationExecutionException;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;

import java.util.Map;

@RequiredArgsConstructor
@Getter(value = AccessLevel.PROTECTED)
public class BaseMongoBucketAggregation implements MongoAggregation {
    private final Map<String, Object> rawDataChunk;

    @Override
    public Bson convertToLanguageSpecific() throws AggregationExecutionException {
        return null;
    }
}
