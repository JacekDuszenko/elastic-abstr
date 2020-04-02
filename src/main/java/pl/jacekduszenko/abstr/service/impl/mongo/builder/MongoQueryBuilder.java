package pl.jacekduszenko.abstr.service.impl.mongo.builder;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.interval.IntervalSupplier;
import pl.jacekduszenko.abstr.service.impl.mongo.types.*;

import java.util.List;
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

    private void addTwoValueCriterionForInferedType(String valueFirst, String valueSecond, Consumer<Tuple2> criterionConsumer) {
        getListOfParsers().stream()
                .map(parser -> tryParseToType(parser, valueFirst, valueSecond))
                .filter(Try::isSuccess)
                .findFirst()
                .ifPresent(successfulTypeInference -> criterionConsumer.accept(successfulTypeInference.get()));
    }

    private List<MongoRangeParser> getListOfParsers() {
        return List.of(
                new MongoRangeIntParser(),
                new MongoRangeDoubleParser(),
                new MongoRangeBooleanParser(),
                new MongoRangeDateParser(),
                new MongoRangeIdentityParser());
    }

    @SuppressWarnings("unchecked")
    private Try<Tuple2> tryParseToType(MongoRangeParser parser, String leftRangeValue, String rightRangeValue) {
        return Try.of(() -> (Tuple2) parser.apply(Tuple.of(leftRangeValue, rightRangeValue)));
    }

    public Query build() {
        return mongoQuery;
    }
}
