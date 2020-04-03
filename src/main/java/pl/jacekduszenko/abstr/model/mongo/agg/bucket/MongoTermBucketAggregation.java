package pl.jacekduszenko.abstr.model.mongo.agg.bucket;

/**
 * Represents group by query in elasticsearch aggregation language
 *
 * Example:
 *
 *   "aggs" : {
 *         "genres" : {
 *             "terms" : { "field" : "genre" }
 *         }
 *     }
 *
 *     This query will group fields with the same "genre" field into buckets.
 */
public class MongoTermBucketAggregation {

}
