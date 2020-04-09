package pl.jacekduszenko.abstr.model.mongo.agg.bucket;

import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.bson.conversions.Bson;
import org.elasticsearch.search.aggregations.AggregationExecutionException;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.mongo.agg.bucket.strategy.impl.MongoBucketAggregationStrategy;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private final static String NO_STRATEGY_FOUND_ERROR_MESSAGE = "Could not find suitable aggregation strategy for term aggregation";

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
        throw new TranslationException(NO_STRATEGY_FOUND_ERROR_MESSAGE);
    }

    private int countNestedAggregations(Map<String, Object> rawDataChunk) {
        AtomicInteger numberOfAggs = new AtomicInteger(0);
        JSONObject queryChunkFromMap = new JSONObject(rawDataChunk);
        Set<String> keySet = queryChunkFromMap.keySet();
        int aggsInCurrentNode = (int) keySet.stream().filter(this::isAggregation).count();
        keySet.stream()
                .filter(key -> isFieldAnObject(queryChunkFromMap, key))
                .forEach(key -> recursivelyCallCountAggregations(numberOfAggs, queryChunkFromMap, key));
        return aggsInCurrentNode + numberOfAggs.get();
    }

    private void recursivelyCallCountAggregations(AtomicInteger numberOfAggs, JSONObject queryChunkFromMap, String key) {
        numberOfAggs.set(numberOfAggs.get() + countNestedAggregations((JSONObject) queryChunkFromMap.get(key)));
    }

    private boolean isFieldAnObject(JSONObject queryChunkFromMap, String key) {
        return queryChunkFromMap.get(key).getClass().equals(JSONObject.class);
    }

    private boolean isAggregation(String key) {
        return AGGREGATION_SHORT_KEY.equals(key) || AGGREGATION_FULL_KEY.equals(key);
    }
}
