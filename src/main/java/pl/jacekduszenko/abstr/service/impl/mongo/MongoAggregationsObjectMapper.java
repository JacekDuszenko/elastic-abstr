package pl.jacekduszenko.abstr.service.impl.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;

import java.util.List;
import java.util.Map;

import static org.apache.commons.compress.utils.Lists.newArrayList;
import static pl.jacekduszenko.abstr.service.impl.mongo.MongoQueryPartExtractor.AGGREGATION_KEY;
import static pl.jacekduszenko.abstr.service.impl.mongo.MongoQueryPartExtractor.AGGREGATION_KEY_LONG;

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

    @SneakyThrows
    private List<MongoAggregation> parseQueryForListOfAggregations(Map<String, Object> aggregationChunk) {
        Map<String, Object> aggregation = getAggregationDataChunk(aggregationChunk);
        String aggregationKeyword = extractSingleKey(aggregation, AGGREGATION_TYPE_NOT_FOUND);
        boolean isAggregationNested = aggregation.keySet().size() > 1;
        Map<String, Object> aggregationSettings = isAggregationNested ? aggregation : (Map<String, Object>) aggregation.get(aggregationKeyword);
        MongoAggregation agg = mongoAggregationModelFactory.createFromKeyword(aggregationKeyword, aggregationSettings);
        return List.of(agg);
    }

    private Map<String, Object> getAggregationDataChunk(Map<String, Object> aggregationChunk) throws TranslationException {
        String aggregationName = extractSingleKey(aggregationChunk, MULTIPLE_AGGREGATIONS_UNSUPPORTED_MSG);
        return (Map<String, Object>) aggregationChunk.get(aggregationName);
    }

    private String extractSingleKey(Map<String, Object> aggregationsNode, String failureMessage) throws TranslationException {
        return extractSingleKeyFromChunk(aggregationsNode, failureMessage);
    }

    private String extractSingleKeyFromChunk(Map<String, Object> dataChunk, String failureMessage) throws TranslationException {
        if (dataChunk.size() != ONE) {
            return extractSingleKeyFromKeySet(dataChunk);
        }
        return dataChunk.keySet().iterator().next();
    }

    private String extractSingleKeyFromKeySet(Map<String, Object> dataChunk) throws TranslationException {
        return
                dataChunk.keySet()
                        .stream()
                        .filter(k -> !(k.equals(AGGREGATION_KEY) || k.equals(AGGREGATION_KEY_LONG)))
                        .findFirst()
                        .orElseThrow(() -> new TranslationException("Aggregation query format is invalid"));
    }
}
