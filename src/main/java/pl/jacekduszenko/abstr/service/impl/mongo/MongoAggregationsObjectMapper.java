package pl.jacekduszenko.abstr.service.impl.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static org.apache.commons.compress.utils.Lists.newArrayList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MongoAggregationsObjectMapper {

    private final ObjectMapper objectMapper;
    private final static String AGGREGATIONS_KEY_SHORT = "aggs";
    private final static String AGGREGATIONS_KEY_LONG = "aggregations";

    private final MongoAggregationsFactory mongoAggregationsFactory;

    List<MongoAggregation> mapQueryStringToAggregations(String queryString) {
        return Try.of(queryToAggregations(queryString))
                .onFailure((ex) -> log.warn("mapping aggregate string query to object failed with exception: {} ", ex.getMessage()))
                .recover((ex) -> newArrayList())
                .get();
    }

    @SuppressWarnings("unchecked")
    private CheckedFunction0<List<MongoAggregation>> queryToAggregations(String queryString) {
        return () -> {
            Map<String, Object> queryChunk = objectMapper.readValue(queryString, Map.class);
            if (queryHasAggregations(queryChunk)) {
                Map<String, Object> aggregationsNode = (Map) extractAggregationsNode(queryChunk);

                // ...

                String aggregationKeyword = "avg";
                Map<String, Object> nestedMap = new HashMap<>();
                MongoAggregation agg = mongoAggregationsFactory.createFromKeyword(aggregationKeyword, nestedMap);
                return List.of(agg);
            }
            return Lists.newArrayList();
        };
    }

    private Object extractAggregationsNode(Map<String, Object> queryChunk) {
        return ofNullable(queryChunk.get(AGGREGATIONS_KEY_SHORT))
                .orElseGet(() -> queryChunk.get(AGGREGATIONS_KEY_LONG));
    }

    private boolean queryHasAggregations(Map<String, Object> queryChunk) {
        return queryChunk.containsKey(AGGREGATIONS_KEY_SHORT) || queryChunk.containsKey(AGGREGATIONS_KEY_LONG);
    }
}
