package pl.jacekduszenko.abstr.service.impl.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoAggregationsObjectMapper {

    private final ObjectMapper objectMapper;

    List<MongoAggregation> mapQueryStringToAggregations(String queryString) {
        return List.of();
    }
}
