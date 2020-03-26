package pl.jacekduszenko.abstr.service.impl.mongo;

import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.exception.MongoAggregationCreationException;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;
import pl.jacekduszenko.abstr.model.mongo.agg.metric.MongoAggregations;

import java.util.Arrays;
import java.util.Map;

@Service
public class MongoAggregationsFactory {

    MongoAggregation createFromKeyword(String keyword, Map<String, Object> dataChunk) throws MongoAggregationCreationException {
        return Arrays.stream(MongoAggregations.values())
                .filter(aggregation -> aggregation.getKeyword().equals(keyword))
                .findFirst()
                .orElseThrow(() -> new MongoAggregationCreationException(String.format("could not create aggregation with keyword: %s", keyword)))
                .newInstance(dataChunk);
    }
}
