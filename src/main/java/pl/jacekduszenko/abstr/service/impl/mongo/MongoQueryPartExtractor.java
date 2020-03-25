package pl.jacekduszenko.abstr.service.impl.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.API;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.service.QueryPartExtractor;

import java.util.Map;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Tuple.of;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoQueryPartExtractor implements QueryPartExtractor<String, String> {

    private final static String QUERY_KEY = "query";
    private final static String AGGREGATION_KEY = "query";

    private final ObjectMapper objectMapper;

    @Override
    public Tuple2<String, String> extractMatchAndAggregatePartFromQuery(String queryString) throws TranslationException {
        try {
            return splitQueryIntoMatchAndAgregateParts(queryString);
        } catch (Exception e) {
            throw new TranslationException(String.format("Query %s is in invalid format", queryString));
        }
    }

    @SuppressWarnings("unchecked")
    private Tuple2<String, String> splitQueryIntoMatchAndAgregateParts(String queryString) throws JsonProcessingException {
        Map<String, Object> map = objectMapper.readValue(queryString, Map.class);
        boolean hasMatch = map.containsKey(QUERY_KEY);
        boolean hasAggregation = map.containsKey(AGGREGATION_KEY);
        return createQueryByPartsExistance(queryString, map, hasMatch, hasAggregation);
    }

    private Tuple2<String, String> createQueryByPartsExistance(String queryString, Map<String, Object> map, boolean hasMatch, boolean hasAggregation) throws JsonProcessingException {
        return API.Match(of(hasMatch, hasAggregation)).of(
                Case($(of(true, true)), of(subqueryToString(QUERY_KEY, map), subqueryToString(AGGREGATION_KEY, map))),
                Case($(of(false, false)), of(queryString, queryString)),
                Case($(of(false, true)), of(queryString, subqueryToString(AGGREGATION_KEY, map))),
                Case($(of(true, false)), of(subqueryToString(QUERY_KEY, map), queryString)));
    }

    private String subqueryToString(String key, Map<String, Object> map) throws JsonProcessingException {
        return asString(map.get(key));
    }

    private String asString(Object subquery) throws JsonProcessingException {
        return objectMapper.writeValueAsString(subquery);
    }
}
