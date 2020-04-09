package pl.jacekduszenko.abstr.model.mongo.agg.bucket.strategy.impl;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Map;

public class ZeroNestedAggregationStrategy implements MongoBucketAggregationStrategy {

    public final static String FIELD_KEY = "field";
    private final static int ONE = 1;
    private final static String COUNT_AGGREGATION = "count";
    private final static String BUCKETS_AGGREGATION_NAME = "buckets";
    private final static String TAKE_ALL_FIELDS_IN_BUCKET_MARKER = "$$ROOT";

    @Override
    public Bson composeAggregation(Map<String, Object> rawQuery) {
        String groupByFieldName = getGroupByFieldName(rawQuery);
        return Aggregates.group(prependDollar(groupByFieldName), countAndBucketAccumulators());
    }

    private List<BsonField> countAndBucketAccumulators() {
        return List.of(
                Accumulators.sum(COUNT_AGGREGATION, ONE),
                Accumulators.push(BUCKETS_AGGREGATION_NAME, TAKE_ALL_FIELDS_IN_BUCKET_MARKER)
        );
    }

    @Override
    public boolean applicable(int numberOfNestedAggregations) {
        return numberOfNestedAggregations == 0;
    }
}
