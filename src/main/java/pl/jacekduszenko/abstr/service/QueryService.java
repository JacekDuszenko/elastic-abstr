package pl.jacekduszenko.abstr.service;

import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;

import java.io.IOException;

public interface QueryService {

    QueryResult search(String elasticQuery) throws IOException, VisitorCreationException;
}
