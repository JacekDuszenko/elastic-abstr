package pl.jacekduszenko.abstr.service.impl.mongo;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;
import pl.jacekduszenko.abstr.service.QueryVisitor;
import pl.jacekduszenko.abstr.service.impl.mongo.visitor.*;


public class MongoVisitorFactoryTest {

    @Test
    public void booleanVistorFactoryTest() throws VisitorCreationException {
        shouldCreateValidVisitorWhenQueryProvided(new BooleanQuery(), BooleanQueryVisitor.class);
    }

    @Test
    public void phraseVistorFactoryTest() throws VisitorCreationException {
        shouldCreateValidVisitorWhenQueryProvided(new PhraseQuery(), PhraseQueryVisitor.class);
    }

    @Test
    public void prefixVistorFactoryTest() throws VisitorCreationException {
        shouldCreateValidVisitorWhenQueryProvided(new PrefixQuery(new Term("test")), PrefixQueryVisitor.class);
    }

    @Test
    public void rangeVistorFactoryTest() throws VisitorCreationException {
        shouldCreateValidVisitorWhenQueryProvided(createMockRangeQuery(), RangeQueryVisitor.class);
    }

    @Test
    public void termVistorFactoryTest() throws VisitorCreationException {
        shouldCreateValidVisitorWhenQueryProvided(new TermQuery(new Term("test")), TermQueryVisitor.class);
    }

    @Test
    public void wildcardVistorFactoryTest() throws VisitorCreationException {
        shouldCreateValidVisitorWhenQueryProvided(new WildcardQuery(new Term("test")), WildcardQueryVisitor.class);
    }

    private void shouldCreateValidVisitorWhenQueryProvided(Query testQuery, Class<? extends QueryVisitor> assertedVisitorClass) throws VisitorCreationException {
        //when
        QueryVisitor qw = MongoVisitorFactory.createVisitorForQuery(testQuery);

        //then
        Assertions.assertTrue(assertedVisitorClass.isInstance(qw));
    }

    private TermRangeQuery createMockRangeQuery() {
        return new TermRangeQuery("test", new BytesRef(), new BytesRef(), true, true);
    }
}