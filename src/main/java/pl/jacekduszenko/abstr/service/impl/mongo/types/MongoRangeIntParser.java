package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class MongoRangeIntParser implements MongoRangeParser<Integer> {

    @Override
    public Tuple2<Integer, Integer> apply(Tuple2<String, String> entryArguments) {
        return entryArguments.map((left, right) -> Tuple.of(Integer.parseInt(left), Integer.parseInt(right)));
    }
}
