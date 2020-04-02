package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple2;

import java.util.function.Function;

public interface MongoRangeParser<R> extends Function<Tuple2<String, String>, Tuple2<R, R>> {
}
