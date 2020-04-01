package pl.jacekduszenko.abstr.service.impl.mongo.builder;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.interval.IntervalSupplier;

import java.util.function.Consumer;

import static pl.jacekduszenko.abstr.integration.BooleanParser.parseBoolean;

public class MongoQueryBuilder {
    private final static String START_MATCH_REGEX = "^";
    private final static String END_MATCH_REGEX = "$";
    private final static String MATCH_NO_CASE_REGEX = "i";

    private final Query mongoQuery;

    public static MongoQueryBuilder create() {
        return new MongoQueryBuilder();
    }

    private MongoQueryBuilder() {
        this.mongoQuery = new Query();
    }

    public void withIsClause(String field, String value) {
        Consumer typeInferenceSuccessConsumer = mongoIsValueConsumer(field);
        Consumer typeInferenceFailureConsumer = mongoIsStringConsumer(field);
        addSingleValueCriterionForInferedType(value, typeInferenceSuccessConsumer, typeInferenceFailureConsumer);
    }

    public void withRangeClause(String field, String lowerTermValue, String upperTermValue, IntervalSupplier intervalSupplier) {
        Consumer<Tuple2> intervalConsumer = intervalSupplier.createIntervalConsumer(mongoQuery, field);
        addTwoValueCriterionForInferedType(lowerTermValue, upperTermValue, intervalConsumer);
    }

    private Consumer mongoIsValueConsumer(String field) {
        return (value) -> mongoQuery.addCriteria(Criteria.where(field).is(value));
    }

    private Consumer mongoIsStringConsumer(String field) {
        return (value) -> mongoQuery.addCriteria(Criteria.where(field).regex(START_MATCH_REGEX + value + END_MATCH_REGEX, MATCH_NO_CASE_REGEX));
    }

    @SuppressWarnings("unchecked")
    private void addSingleValueCriterionForInferedType(String value, Consumer parseSuccessConsumer, Consumer parseFailureConsumer) {
        Try.of(() -> Integer.parseInt(value))
                .onSuccess(parseSuccessConsumer)
                .onFailure(e -> Try.of(() -> Double.parseDouble(value))
                        .onSuccess(parseSuccessConsumer)
                        .onFailure(x -> Try.of(() -> parseBoolean(value))
                                .onSuccess(parseSuccessConsumer)
                                .onFailure(c -> parseFailureConsumer.accept(value))));
    }

    //TODO sequenced processing instead of onX pipeline
    private void addTwoValueCriterionForInferedType(String valueFirst, String valueSecond, Consumer<Tuple2> criterionConsumer) {
        Try.of(() -> Tuple.of(Integer.parseInt(valueFirst), Integer.parseInt(valueSecond)))
                .onSuccess(criterionConsumer)
                .onFailure(e -> Try.of(() -> Tuple.of(Double.parseDouble(valueFirst), Double.parseDouble(valueSecond)))
                        .onSuccess(criterionConsumer)
                        .onFailure(x -> Try.of(() -> Tuple.of(parseBoolean(valueFirst), parseBoolean(valueSecond)))
                                .onSuccess(criterionConsumer)
                                .onFailure(c -> criterionConsumer.accept(Tuple.of(valueFirst, valueSecond)))));
    }

    public Query build() {
        return mongoQuery;
    }
}
