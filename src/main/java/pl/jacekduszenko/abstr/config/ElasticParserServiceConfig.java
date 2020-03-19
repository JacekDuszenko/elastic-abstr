package pl.jacekduszenko.abstr.config;

import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.inject.ModulesBuilder;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.fielddata.IndexFieldDataService;
import org.elasticsearch.index.mapper.MapperService;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jacekduszenko.abstr.integration.StubIndexService;
import pl.jacekduszenko.abstr.service.factory.ElasticModulesFactory;

import java.util.List;

@Configuration
public class ElasticParserServiceConfig {

    private static final String INDEX_CACHE_FILTER_TYPE_KEY = "index.cache.filter.type";
    private static final String NAME_KEY = "name";
    private static final String NONE_VALUE = "none";
    private static final String INDEX_QUERY_PARSER_NAME = "IndexQueryParser";
    private static final String PARSE_INDEX_NAME = "parseIndex";

    @Bean
    IndexQueryParserService indexQueryParserService() {
        Settings settings = createElasticSettings();
        Index index = createStubParseIndex();
        Injector injector = intializeElasticInjector(settings, index);
        setStubIndexService(injector);

        return injector.getInstance(IndexQueryParserService.class);
    }

    private void setStubIndexService(Injector injector) {
        injector.getInstance(IndexFieldDataService.class).setIndexService((new StubIndexService(injector.getInstance(MapperService.class))));
    }

    private Settings createElasticSettings() {
        return ImmutableSettings.settingsBuilder()
                .put(INDEX_CACHE_FILTER_TYPE_KEY, NONE_VALUE)
                .put(NAME_KEY, INDEX_QUERY_PARSER_NAME)
                .build();
    }

    private Index createStubParseIndex() {
        return new Index(PARSE_INDEX_NAME);
    }

    private Injector intializeElasticInjector(Settings settings, Index index) {
        ModulesBuilder builder = new ModulesBuilder();
        List<Module> elasticModules = createElasticModulesForParser(settings, index);
        elasticModules.forEach(builder::add);
        return builder.createInjector();
    }

    private List<Module> createElasticModulesForParser(Settings settings, Index index) {
        return ElasticModulesFactory.createFromSettingsAndIndex(settings, index);
    }
}
