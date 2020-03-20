package pl.jacekduszenko.abstr.service;

import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;

public interface QueryVisitor<Q, B> {

    void visit(Q query, B builder) throws VisitorCreationException;
}
