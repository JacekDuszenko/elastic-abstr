package pl.jacekduszenko.abstr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.query.ParsedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.service.QueryService;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoQueryService implements QueryService {

    private final IndexQueryParserService indexQueryParserService;

    private static final String exampleQuery = " {" +
            "         \"boosting\" : {\n" +
            "            \"positive\" : {\n" +
            "                \"term\" : {\n" +
            "                    \"text\" : \"apple\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"negative\" : {\n" +
            "                 \"term\" : {\n" +
            "                     \"text\" : \"pie tart fruit crumble tree\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"negative_boost\" : 0.5\n" +
            "        }" +
            "} ";

    public QueryResult translateQuery(String elasticQuery) {
        ParsedQuery pq = indexQueryParserService.parse(exampleQuery);
        Query luceneQuery = pq.query();
        log.info(luceneQuery.toString());
        return null;
    }
}
