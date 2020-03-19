package pl.jacekduszenko.abstr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.service.QueryService;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoQueryService implements QueryService {

    private final IndexQueryParserService indexQueryParserService;

    public QueryResult search(String elasticQuery) {
        Query luceneQuery = indexQueryParserService.parse(elasticQuery).query();
        logExtractedTerms(luceneQuery);

        //TODO 1. write a custom lucene query parser to translate into mongo syntax
        //TODO 2. execute created mongo dsl against embedded db and return results

        return new QueryResult();
    }

    private void logExtractedTerms(Query luceneQuery) {
        Set<Term> terms = new HashSet<>();
        luceneQuery.extractTerms(terms);
        terms.forEach(t -> log.info(t.toString()));
    }
}
