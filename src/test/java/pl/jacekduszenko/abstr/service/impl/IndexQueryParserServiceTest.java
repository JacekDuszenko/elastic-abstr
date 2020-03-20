package pl.jacekduszenko.abstr.service.impl;

import org.apache.lucene.queries.BoostingQuery;
import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.query.ParsedQuery;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jacekduszenko.abstr.config.ElasticParserServiceConfig;
import pl.jacekduszenko.abstr.data.ElasticsearchQueryLoader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticParserServiceConfig.class)
public class IndexQueryParserServiceTest {

    private final static String validLuceneBoostingDsl = "text:apple/text:pie tart fruit crumble tree^0.0";
    private final static String validLuceneMatchAllDsl = "ConstantScore(*:*)";
    @Autowired
    private IndexQueryParserService indexQueryParserService;

    @Test
    public void shouldParseBoostingQueryCorrectly() {
        testQueryFromFile("boosting_query.json", validLuceneBoostingDsl, BoostingQuery.class);
    }

    @Test
    public void shouldParseMatchAllQueryCorrectly() {
        testQueryFromFile("match_all_query.json", validLuceneMatchAllDsl, ConstantScoreQuery.class);
    }   

    private void testQueryFromFile(String filename, String assertedLuceneQuery, Class assertedClass) {
        //given
        String contents = ElasticsearchQueryLoader.loadElasticQueryFromFile(filename);

        //when
        ParsedQuery parsedQuery = indexQueryParserService.parse(contents);
        Query query = parsedQuery.query();

        //then
        assertThat(query.toString(), Matchers.is(assertedLuceneQuery));
        assertTrue(assertedClass.isInstance(query));
    }
}
