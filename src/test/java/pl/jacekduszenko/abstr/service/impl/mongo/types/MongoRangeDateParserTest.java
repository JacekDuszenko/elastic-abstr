package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

class MongoRangeDateParserTest {

    private MongoRangeDateParser mongoRangeDateParser;
    public static final String validLeftDate = "2020-03-03t00:00:00.000z";
    public static final String validRightDate = "2020-03-06t00:00:00.000z";

    @BeforeEach
    void setUp() {
        mongoRangeDateParser = new MongoRangeDateParser();
    }

    @Test
    void shouldParseDateCorrectly() {
        //given
        Tuple2<String, String> validDates = Tuple.of(validLeftDate, validRightDate);

        //when
        Tuple2<Date, Date> parsedDates = mongoRangeDateParser.apply(validDates);

        //then no exception thrown and assert later
        System.out.println(parsedDates);

    }
}