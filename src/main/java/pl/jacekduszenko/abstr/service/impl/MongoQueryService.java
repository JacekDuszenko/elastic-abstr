package pl.jacekduszenko.abstr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.service.QueryService;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoQueryService implements QueryService {
    public QueryResult translateQuery(String elasticQuery) throws IOException {
        WrapperQueryBuilder wrapperQueryBuilder = new WrapperQueryBuilder(elasticQuery);
        return null;
    }
}
