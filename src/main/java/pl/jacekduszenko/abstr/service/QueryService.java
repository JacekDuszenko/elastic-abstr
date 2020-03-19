package pl.jacekduszenko.abstr.service;

import pl.jacekduszenko.abstr.model.QueryResult;

import java.io.IOException;

public interface QueryService {

    QueryResult search(String elasticQuery) throws IOException;
}
