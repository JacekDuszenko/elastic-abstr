package pl.jacekduszenko.abstr.service;

import pl.jacekduszenko.abstr.model.QueryResult;

public interface QueryService {

    QueryResult translateQuery(String elasticQuery);
}
