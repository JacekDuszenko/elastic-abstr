package pl.jacekduszenko.abstr.model.mongo.agg.bucket;

import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.bson.conversions.Bson;
import org.elasticsearch.search.aggregations.AggregationExecutionException;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.mongo.agg.bucket.strategy.impl.MongoBucketAggregationStrategy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents group by query in elasticsearch aggregation language.
 * Example:
 * <p
 * "aggs" : {
 * "genres" : {
 * "terms" : {
 * "field" : "genre"
 * }
 * }
 * }
 * </p>
 * This query will group fields with the same "genre" field into buckets.
 */

public class MongoTermBucketAggregation extends BaseMongoBucketAggregation {

    private final static String AGGREGATION_SHORT_KEY = "aggs";
    private final static String AGGREGATION_FULL_KEY = "aggregations";

    private final List<MongoBucketAggregationStrategy> aggregationStrats;

    public MongoTermBucketAggregation(Map<String, Object> rawDataChunk, List<MongoBucketAggregationStrategy> aggregationStrats) {
        super(rawDataChunk);
        this.aggregationStrats = aggregationStrats;
    }

    @Override
    @SneakyThrows
    public Bson convertToLanguageSpecific() throws AggregationExecutionException {
        int nestedAggregationsNumber = countNestedAggregations(this.getRawDataChunk());
        for (MongoBucketAggregationStrategy strat : this.aggregationStrats) {
            if (strat.applicable(nestedAggregationsNumber)) {
                return strat.composeAggregation(this.getRawDataChunk());
            }
        }
        throw new TranslationException("Could not find suitable aggregation strategy for term aggregation");
    }

    private int countNestedAggregations(Map<String, Object> rawDataChunk) {
        AtomicInteger count = new AtomicInteger();
        JSONObject queryChunkFromMap = new JSONObject(rawDataChunk);
        queryChunkFromMap
                .keySet()
                .stream()
                .filter(this::isAggregation)
                .peek(key -> count.incrementAndGet())
                .forEach(key -> count.set(count.get() + countNestedAggregations((JSONObject) queryChunkFromMap.get(key))));
        return count.get();
    }

    private boolean isAggregation(String key) {
        return AGGREGATION_SHORT_KEY.equals(key) || AGGREGATION_FULL_KEY.equals(key);
    }
}
