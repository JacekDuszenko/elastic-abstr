package pl.jacekduszenko.abstr.data;

import lombok.SneakyThrows;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

public class LuceneQueryProvider {
    private static QueryParser qp = new QueryParser(Version.LUCENE_4_9, "text", new WhitespaceAnalyzer(Version.LUCENE_4_9));

    @SneakyThrows
    public static Query fromString(String luceneQueryString) {
        return qp.parse(luceneQueryString);
    }
}
