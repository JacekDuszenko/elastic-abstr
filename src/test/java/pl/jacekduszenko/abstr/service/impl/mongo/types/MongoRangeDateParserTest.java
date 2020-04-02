package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

class MongoRangeDateParserTest {

    private MongoRangeDateParser mongoRangeDateParser;

    @BeforeEach
    void setUp() {
        mongoRangeDateParser = new MongoRangeDateParser();
    }

    @Test
    void shouldParseDateCorrectly() {

        //given
        Tuple2 validDates = Tuple.of();
        
        //when
        Tuple2<Date,Date> parsedDates = .apply()
    }
}