package es.query.builder;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class ElasticQueryStringBuilder {

    private static final String MATCH_KEY = "match";
    private static final String MUST_KEY = "must";
    private static final String BOOL_KEY = "bool";

    /**
     * @param keyValuePair list of query key - value pairs.
     * @return string elasticsearch query
     */
    public String createMatchQuery(List<Tuple2<String, Object>> keyValuePair) {
        JSONArray mustObject = new JSONArray();
        keyValuePair.map(this::tupleToMatchObject)
                .forEach(mustObject::appendElement);
        return new JSONObject()
                .appendField(BOOL_KEY, new JSONObject()
                        .appendField(MUST_KEY, mustObject)).toJSONString();
    }

    private JSONObject tupleToMatchObject(Tuple2<String, Object> tpl) {
        return new JSONObject().appendField(MATCH_KEY, new JSONObject().appendField(tpl._1, tpl._2));
    }
}