package pl.jacekduszenko.abstr.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.service.QueryService;

import java.io.IOException;

@Service
@Slf4j
public class MongoQueryService implements QueryService {
    @Override
    public QueryResult translateQuery(String elasticQuery) throws IOException {
        WrapperQueryBuilder wrapperQueryBuilder = new WrapperQueryBuilder(elasticQuery);
        //TODO get context from embedded elastic to translate elastic query into lucene DSL
        // wrapperQueryBuilder.toQuery()

        return null;
    }
}
