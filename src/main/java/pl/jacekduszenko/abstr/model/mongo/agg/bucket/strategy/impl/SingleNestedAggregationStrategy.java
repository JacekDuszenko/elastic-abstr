package pl.jacekduszenko.abstr.model.mongo.agg.bucket.strategy.impl;

import com.mongodb.client.model.Aggregates;
import lombok.SneakyThrows;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;
import pl.jacekduszenko.abstr.model.exception.TranslationException;

import java.util.Map;

import static pl.jacekduszenko.abstr.service.impl.mongo.MongoQueryPartExtractor.AGGREGATION_KEY;
import static pl.jacekduszenko.abstr.service.impl.mongo.MongoQueryPartExtractor.AGGREGATION_KEY_LONG;

@Component
public class SingleNestedAggregationStrategy implements MongoBucketAggregationStrategy {

    @Override
    public boolean applicable(int numberOfNestedAggregations) {
        return numberOfNestedAggregations == 1;
    }

    @Override
    @SneakyThrows
    public Bson composeAggregation(Map<String, Object> rawQuery) {
        Map<String, Object> topLevelAggregation = extractTopLevelAggregation(rawQuery);
        String groupByFieldName = getGroupByFieldName(topLevelAggregation);
        BsonArray bsar = new BsonArray();
        Aggregates.group(prependDollar(groupByFieldName));
        Document document = new Document();
        Map<String, Object> nestedAggregation = extractNestedAggregation(rawQuery);
        System.out.println("XD");
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> extractTopLevelAggregation(Map<String, Object> rawQuery) throws TranslationException {
        String nestedAggregationKey = rawQuery.keySet()
                .stream()
                .filter(k -> isNotAggregationKeyword(rawQuery))
                .findFirst()
                .orElseThrow(() -> new TranslationException("Could not find top level aggregation settings"));
        return (Map<String, Object>) rawQuery.get(nestedAggregationKey);
    }

    private boolean isNotAggregationKeyword(Map<String, Object> rawQuery) {
        return rawQuery.containsKey(AGGREGATION_KEY) || rawQuery.containsKey(AGGREGATION_KEY_LONG);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> extractNestedAggregation(Map<String, Object> map) throws TranslationException {
        Map<String, Object> nestedAggregation =  (Map<String, Object>) map.getOrDefault(AGGREGATION_KEY_LONG, map.getOrDefault(AGGREGATION_KEY, Map.of()));
        return getFirstKeyFromNestedAggregation(nestedAggregation);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getFirstKeyFromNestedAggregation(Map<String, Object> nestedAggregation) throws TranslationException {
        String key = nestedAggregation.keySet().stream().findFirst().orElseThrow(() -> new TranslationException("Could not find query name in nested aggregation"));
        return (Map<String, Object>) nestedAggregation.get(key);
    }
}
