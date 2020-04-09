package pl.jacekduszenko.abstr.service.impl.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.exception.MongoAggregationCreationException;
import pl.jacekduszenko.abstr.model.exception.UnsupportedQueryFormatException;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;

import java.util.List;
import java.util.Map;

import static org.apache.commons.compress.utils.Lists.newArrayList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@SuppressWarnings("unchecked")
public class MongoAggregationsObjectMapper {
    private final static int ONE = 1;
    private static final String MULTIPLE_AGGREGATIONS_UNSUPPORTED_MSG = "Multiple aggregations are currently unsupported";
    private static final String AGGREGATION_TYPE_NOT_FOUND = "Aggregation type not found";

    private final ObjectMapper objectMapper;

    private final MongoAggregationModelFactory mongoAggregationModelFactory;

    List<MongoAggregation> mapQueryStringToAggregations(String queryString) {
        return Try.of(queryToAggregations(queryString))
                .onFailure((ex) -> log.warn("mapping aggregate string query to object failed with exception: {} ", ex.getMessage()))
                .recover((ex) -> newArrayList())
                .get();
    }

    private CheckedFunction0<List<MongoAggregation>> queryToAggregations(String queryString) {
        return () -> {
            Map<String, Object> aggregationChunk = objectMapper.readValue(queryString, Map.class);
            return parseQueryForListOfAggregations(aggregationChunk);
        };
    }

    private List<MongoAggregation> parseQueryForListOfAggregations(Map<String, Object> aggregationChunk) throws UnsupportedQueryFormatException, MongoAggregationCreationException {
        Map<String, Object> aggregation = getAggregationDataChunk(aggregationChunk);
        String aggregationKeyword = extractSingleKey(aggregation, AGGREGATION_TYPE_NOT_FOUND);
        Map<String, Object> aggregationSettings = (Map<String, Object>) aggregation.get(aggregationKeyword);
        MongoAggregation agg = mongoAggregationModelFactory.createFromKeyword(aggregationKeyword, aggregationSettings);
        return List.of(agg);
    }

    private Map<String, Object> getAggregationDataChunk(Map<String, Object> aggregationChunk) throws UnsupportedQueryFormatException {
        String aggregationName = extractSingleKey(aggregationChunk, MULTIPLE_AGGREGATIONS_UNSUPPORTED_MSG);
        return (Map<String, Object>) aggregationChunk.get(aggregationName);
    }

    private String extractSingleKey(Map<String, Object> aggregationsNode, String failureMessage) throws UnsupportedQueryFormatException {
        return extractSingleKeyFromChunk(aggregationsNode, failureMessage);
    }

    private String extractSingleKeyFromChunk(Map<String, Object> dataChunk, String failureMessage) throws UnsupportedQueryFormatException {
        if (dataChunk.size() != ONE) {
            throw new UnsupportedQueryFormatException(failureMessage);
        }
        return dataChunk.keySet().iterator().next();
    }
}
