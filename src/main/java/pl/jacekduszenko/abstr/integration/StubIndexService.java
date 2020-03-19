package pl.jacekduszenko.abstr.integration;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.collect.ImmutableSet;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexShardMissingException;
import org.elasticsearch.index.aliases.IndexAliasesService;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.cache.IndexCache;
import org.elasticsearch.index.engine.IndexEngine;
import org.elasticsearch.index.fielddata.IndexFieldDataService;
import org.elasticsearch.index.gateway.IndexGateway;
import org.elasticsearch.index.mapper.MapperService;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.service.IndexService;
import org.elasticsearch.index.settings.IndexSettingsService;
import org.elasticsearch.index.shard.service.IndexShard;
import org.elasticsearch.index.similarity.SimilarityService;
import org.elasticsearch.index.store.IndexStore;

import java.util.Iterator;

public class StubIndexService implements IndexService {
    private final MapperService mapperService;

    public StubIndexService(MapperService mapperService) {
        this.mapperService = mapperService;
    }

    @Override
    public Injector injector() {
        return null;
    }

    @Override
    public IndexGateway gateway() {
        return null;
    }

    @Override
    public IndexCache cache() {
        return null;
    }

    @Override
    public IndexFieldDataService fieldData() {
        return null;
    }

    @Override
    public IndexSettingsService settingsService() {
        return null;
    }

    @Override
    public AnalysisService analysisService() {
        return null;
    }

    @Override
    public MapperService mapperService() {
        return mapperService;
    }

    @Override
    public IndexQueryParserService queryParserService() {
        return null;
    }

    @Override
    public SimilarityService similarityService() {
        return null;
    }

    @Override
    public IndexAliasesService aliasesService() {
        return null;
    }

    @Override
    public IndexEngine engine() {
        return null;
    }

    @Override
    public IndexStore store() {
        return null;
    }

    @Override
    public IndexShard createShard(int sShardId) throws ElasticsearchException {
        return null;
    }

    @Override
    public void removeShard(int shardId, String reason) throws ElasticsearchException {
    }

    @Override
    public int numberOfShards() {
        return 0;
    }

    @Override
    public ImmutableSet<Integer> shardIds() {
        return null;
    }

    @Override
    public boolean hasShard(int shardId) {
        return false;
    }

    @Override
    public IndexShard shard(int shardId) {
        return null;
    }

    @Override
    public IndexShard shardSafe(int shardId) throws IndexShardMissingException {
        return null;
    }

    @Override
    public Injector shardInjector(int shardId) {
        return null;
    }

    @Override
    public Injector shardInjectorSafe(int shardId) throws IndexShardMissingException {
        return null;
    }

    @Override
    public String indexUUID() {
        return IndexMetaData.INDEX_UUID_NA_VALUE;
    }

    @Override
    public Index index() {
        return null;
    }

    @Override
    public Iterator<IndexShard> iterator() {
        return null;
    }
}
