package pl.jacekduszenko.abstr.model.mongo.agg.metric;

import lombok.Getter;
import pl.jacekduszenko.abstr.model.mongo.agg.MongoAggregation;

import java.util.Map;

@Getter
public enum MongoAggregations {
    AVG("avg") {
        @Override
        public MongoAggregation newInstance(Map<String, Object> dataChunk) {
            return new AvgAggregation(dataChunk);
        }

    }, COUNT("cardinality") {
        @Override
        public MongoAggregation newInstance(Map<String, Object> dataChunk) {
            return new CountAggregation(dataChunk);
        }

    }, MAX("max") {
        @Override
        public MongoAggregation newInstance(Map<String, Object> dataChunk) {
            return new MaxAggregation(dataChunk);
        }

    }, MIN("min") {
        @Override
        public MongoAggregation newInstance(Map<String, Object> dataChunk) {
            return new MinAggregation(dataChunk);
        }

    }, STATS("stats") {
        @Override
        public MongoAggregation newInstance(Map<String, Object> dataChunk) {
            return new StatsAggregation(dataChunk);
        }

    }, STATS_STRING("stats_string") {
        @Override
        public MongoAggregation newInstance(Map<String, Object> dataChunk) {
            return new StringStatsAggregation(dataChunk);
        }

    }, SUM("sum") {
        @Override
        public MongoAggregation newInstance(Map<String, Object> dataChunk) {
            return new SumAggregation(dataChunk);
        }
    };

    private String keyword;

    MongoAggregations(String keyword) {
        this.keyword = keyword;
    }

    public abstract MongoAggregation newInstance(Map<String, Object> dataChunk);
}
