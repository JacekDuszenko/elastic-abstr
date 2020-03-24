package pl.jacekduszenko.abstr.service.impl.mongo.visitor;

import io.vavr.Tuple;
import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.service.QueryVisitor;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.interval.BothClosedIntervalSupplier;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.interval.BothOpenintervalSupplier;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.interval.LeftOpenRightClosedIntervalSupplier;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.interval.RightOpenLeftClosedIntervalSupplier;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.vavr.API.*;
import static io.vavr.Tuple.of;
import static java.util.Optional.ofNullable;

@Slf4j
public class RangeQueryVisitor implements QueryVisitor<TermRangeQuery, MongoQueryBuilder> {

    @Override
    public void visit(TermRangeQuery query, MongoQueryBuilder builder) throws TranslationException {
        Objects.requireNonNull(query);

        String lowerTermValue = extractTermValueFromQuery(query.getLowerTerm());
        String upperTermValue = extractTermValueFromQuery(query.getUpperTerm());
        String field = query.getField();

        Tuple intervalEdges = Tuple.of(query.includesLower(), query.includesUpper());
        Match(intervalEdges).of(
                Case($(of(true, true)), new BothClosedIntervalSupplier(builder, field, lowerTermValue, upperTermValue)),
                Case($(of(false, true)), new LeftOpenRightClosedIntervalSupplier(builder, field, lowerTermValue, upperTermValue)),
                Case($(of(true, false)), new RightOpenLeftClosedIntervalSupplier(builder, field, lowerTermValue, upperTermValue)),
                Case($(of(false, false)), new BothOpenintervalSupplier(builder, field, lowerTermValue, upperTermValue))
        );
    }

    private String extractTermValueFromQuery(BytesRef bytesRef) throws TranslationException {
        return ofNullable(bytesRef)
                .map(ref -> ref.bytes)
                .map(toUTF8String())
                .orElseThrow(() -> new TranslationException(("could not extract value from range query term")));
    }

    private Function<byte[], String> toUTF8String() {
        return bytes -> {
            Byte[] trimmedBytes = List.ofAll(Arrays.asList(ArrayUtils.toObject(bytes)))
                    .dropRightWhile(isByteNull())
                    .toJavaList().toArray(new Byte[]{});
            return new String(ArrayUtils.toPrimitive(trimmedBytes), StandardCharsets.UTF_8);
        };
    }

    private Predicate<Byte> isByteNull() {
        return b -> b == 0;
    }
}