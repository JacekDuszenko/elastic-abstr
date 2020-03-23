package pl.jacekduszenko.abstr.service;

import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;

import java.io.IOException;
import java.util.List;

public interface QueryService {

    List search(String elasticQuery, String collection, Boolean verbose) throws IOException, VisitorCreationException, TranslationException;
}
