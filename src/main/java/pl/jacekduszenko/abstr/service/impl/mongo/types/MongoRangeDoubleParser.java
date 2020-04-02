package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class MongoRangeDoubleParser implements MongoRangeParser<Double> {

    @Override
    public Tuple2<Double, Double> apply(Tuple2<String, String> entryArguments) {
        return entryArguments.map((left, right) -> Tuple.of(Double.parseDouble(left), Double.parseDouble(right)));
    }
}
