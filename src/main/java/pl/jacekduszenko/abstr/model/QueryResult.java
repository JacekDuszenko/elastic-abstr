package pl.jacekduszenko.abstr.model;

import lombok.Data;

import java.util.Map;

@Data
public class QueryResult {

    private Map<String, Object> rawResult;
}
