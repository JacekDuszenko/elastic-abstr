package pl.jacekduszenko.abstr.service.impl.mongo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;
import pl.jacekduszenko.abstr.service.QueryService;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoQueryService implements QueryService {

    private final IndexQueryParserService indexQueryParserService;
    private final LuceneToMongoTranslator luceneToMongoTranslator;

    public QueryResult search(String elasticQuery) throws VisitorCreationException {
        Query luceneQuery = indexQueryParserService.parse(elasticQuery).query();
        luceneToMongoTranslator.translateFromLuceneQuery(luceneQuery);
        return new QueryResult();
    }
}
