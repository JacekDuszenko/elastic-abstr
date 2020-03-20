package pl.jacekduszenko.abstr.service.impl.mongo;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import pl.jacekduszenko.abstr.model.MongoQueryBuilder;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;
import pl.jacekduszenko.abstr.service.QueryVisitor;

import java.util.Map;

import static pl.jacekduszenko.abstr.service.impl.mongo.QueryToVisitorMapping.queryToVisitorMapping;

@Slf4j
public class MongoVisitorFactory {

    private static Map<Class<? extends Query>, Class<? extends QueryVisitor>> queryClasses = queryToVisitorMapping();

    public static QueryVisitor<? extends Query, MongoQueryBuilder> createVisitorForQuery(Query luceneQuery) throws VisitorCreationException {
        Class<? extends QueryVisitor> visitorClass = queryClasses.get(luceneQuery.getClass());
        if (visitorClass == null) {
            throw new VisitorCreationException(notFoundMessage(luceneQuery));
        }
        return tryCreateVisitorInstance(visitorClass);
    }

    private static QueryVisitor<? extends Query, MongoQueryBuilder> tryCreateVisitorInstance(Class visitorClass) throws VisitorCreationException {
        try {
            return createVisitorInstance(visitorClass);
        } catch (Exception ex) {
            logErrorAndRethrow(visitorClass);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static QueryVisitor<? extends Query, MongoQueryBuilder> createVisitorInstance(Class visitorClass) throws Exception {
        return (QueryVisitor) visitorClass.getDeclaredConstructor().newInstance();
    }

    private static void logErrorAndRethrow(Class visitorClass) throws VisitorCreationException {
        String creationErrorMessage = String.format("Could not create instance of %s class", visitorClass.toString());
        log.error(creationErrorMessage);
        throw new VisitorCreationException(creationErrorMessage);
    }

    private static String notFoundMessage(Query luceneQuery) {
        return String.format("Could not find matching visitor for query: %s", luceneQuery.toString());
    }
}
