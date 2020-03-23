package pl.jacekduszenko.abstr.controller;

import es.query.builder.ElasticQueryStringBuilder;
import io.vavr.Tuple;
import io.vavr.collection.List;
import org.bson.Document;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.jacekduszenko.abstr.AbstrApplication;
import pl.jacekduszenko.abstr.api.ApiCaller;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AbstrApplication.class)
@AutoConfigureMockMvc
public class MongoQueryIntegrationTest {

    private final ElasticQueryStringBuilder builder = new ElasticQueryStringBuilder();
    private final String mockMongoData = "mockMongoData";

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MockMvc mockMvc;

    private ApiCaller apiCaller;

    @Before
    public void setUp() {
        mongoTemplate.getDb().drop();
        apiCaller = new ApiCaller(mockMvc);
    }

    @Test
    public void shouldReturnOnlyMatchingRecord() throws Exception {
        //given
        insertThreeValueRecordToMockMongoDataCollection("developer", 100, true);
        insertThreeValueRecordToMockMongoDataCollection("notMatchingRecord", 200, false);

        //when
        String queryString = builder.createMatchQuery(List.of(Tuple.of("name", "developer"), Tuple.of("age", 100), Tuple.of("finished", true)));
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, mockMongoData, false);

        //then
        MatcherAssert.assertThat(docs.size(), is(1));
        MatcherAssert.assertThat(docs.get(0).get("name"), is("developer"));
        MatcherAssert.assertThat(docs.get(0).get("age"), is(100));
        MatcherAssert.assertThat(docs.get(0).get("finished"), is(true));

    }

    private void insertThreeValueRecordToMockMongoDataCollection(String stringValue, Integer intValue, Boolean boolValue) {
        insertData(Map.of("name", stringValue, "age", intValue, "finished", boolValue), mockMongoData);
    }

    private void insertData(Map<String, Object> data, String collectionName) {
        Document document = new Document(data);
        mongoTemplate.insert(document, collectionName);
    }
}