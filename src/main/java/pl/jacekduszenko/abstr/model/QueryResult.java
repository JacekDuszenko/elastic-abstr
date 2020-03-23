package pl.jacekduszenko.abstr.model;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class QueryResult {

    private final Map<String, Object> rawResult;
}
