package pl.jacekduszenko.abstr.model;

import io.vavr.control.Try;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.function.Consumer;

public class MongoQueryBuilder {
    private final static String START_MATCH_REGEX = "^";
    private final static String END_MATCH_REGEX = "$";
    private final static String MATCH_NO_CASE_REGEX = "i";
    private static final String TRUE_STRING = "true";
    private static final String FALSE_STRING = "false";

    private final Query mongoQuery;


    public static MongoQueryBuilder create() {
        return new MongoQueryBuilder();
    }

    private MongoQueryBuilder() {
        this.mongoQuery = new Query();
    }

    @SuppressWarnings("unchecked")
    public void withIsClause(String field, String value) {
        Try.of(() -> Integer.parseInt(value))
                .onSuccess(addMongoValueQuery(field))
                .onFailure(e -> Try.of(() -> Double.parseDouble(value))
                        .onSuccess(addMongoValueQuery(field))
                        .onFailure(x -> Try.of(() -> parseBoolean(value))
                                .onSuccess(addMongoValueQuery(field))
                                .onFailure(c -> addStringCriteria(field, value))));
    }

    private void addStringCriteria(String field, String value) {
        mongoQuery.addCriteria(Criteria.where(field).regex(START_MATCH_REGEX + value + END_MATCH_REGEX, MATCH_NO_CASE_REGEX));
    }

    private Consumer addMongoValueQuery(String field) {
        return (value) -> mongoQuery.addCriteria(Criteria.where(field).is(value));
    }

    private Boolean parseBoolean(String value) throws Exception {
        if (isBooleanString(value)) {
            return Boolean.parseBoolean(value);
        } else {
            throw new Exception("Value is not boolean.");
        }
    }

    private boolean isBooleanString(String value) {
        return TRUE_STRING.equals(value) || FALSE_STRING.equals(value);
    }

    public Query build() {
        return mongoQuery;
    }
}
