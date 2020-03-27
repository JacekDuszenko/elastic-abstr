package pl.jacekduszenko.abstr.service.impl.mongo;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;
import pl.jacekduszenko.abstr.service.AggregationFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoAggregationFactory implements AggregationFactory<List<Bson>, String> {

    private final MongoAggregationsObjectMapper mongoAggregationsObjectMapper;

    public List<Bson> fromAggregationString(String aggregationsQuery) {
        List<MongoAggregation> aggs = mongoAggregationsObjectMapper.mapQueryStringToAggregations(aggregationsQuery);
        return convertAggregationsToBson(aggs);
    }

    private List<Bson> convertAggregationsToBson(List<MongoAggregation> aggs) {
        return Try.of(() ->
                aggs.stream().map(MongoAggregation::convertToLanguageSpecific).collect(Collectors.toList()))
                .recover((ex) -> Lists.newArrayList())
                .get();
    }
}
