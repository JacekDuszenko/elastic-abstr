package pl.jacekduszenko.abstr.service.impl;

import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.service.QueryService;

@Service
public class MongoQueryService implements QueryService {

    @Override
    public QueryResult translateQuery(String elasticQuery) {
        return null;
    }
}
