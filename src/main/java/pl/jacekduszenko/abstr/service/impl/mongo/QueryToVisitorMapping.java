package pl.jacekduszenko.abstr.service.impl.mongo;

import org.apache.lucene.search.*;
import pl.jacekduszenko.abstr.service.QueryVisitor;
import pl.jacekduszenko.abstr.service.impl.mongo.visitor.*;

import java.util.Map;

public class QueryToVisitorMapping {
    static Map<Class<? extends Query>, Class<? extends QueryVisitor>> queryToVisitorMapping() {
        return Map.of(
                BooleanQuery.class, BooleanQueryVisitor.class,
                PhraseQuery.class, PhraseQueryVisitor.class,
                PrefixQuery.class, PrefixQueryVisitor.class,
                TermRangeQuery.class, RangeQueryVisitor.class,
                TermQuery.class, TermQueryVisitor.class,
                WildcardQuery.class, WildcardQueryVisitor.class
        );
    }
}
