package pl.jacekduszenko.abstr.service.impl;

import org.apache.lucene.search.Query;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jacekduszenko.abstr.config.ElasticParserServiceConfig;
import pl.jacekduszenko.abstr.data.ElasticsearchQueryLoader;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticParserServiceConfig.class)
public class IndexQueryParserServiceTest {

    private final static String validLuceneDslQueryString = "text:apple/text:pie tart fruit crumble tree^0.0";
    @Autowired
    private IndexQueryParserService indexQueryParserService;

    @Test
    public void shouldParseQueryCorrectly() {
        //given
        String contents = ElasticsearchQueryLoader.loadElasticQueryFromFile("query1.json");

        //when
        Query query = indexQueryParserService.parse(contents).query();

        //then
        assertThat(query.toString(), Matchers.is(validLuceneDslQueryString));
    }
}
