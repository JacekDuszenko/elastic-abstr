package pl.jacekduszenko.abstr.service.impl.mongo;

import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.MongoAggregationQuery;

import java.util.List;

@Service
public class MongoAggregationsObjectMapper {
    List<MongoAggregationQuery> mapQueryStringToAggregations(String queryString) {
        return List.of();
    }
}
