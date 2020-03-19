package pl.jacekduszenko.abstr.integration;

import org.elasticsearch.cluster.ClusterService;
import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.inject.util.Providers;
import org.elasticsearch.indices.fielddata.breaker.CircuitBreakerService;
import org.elasticsearch.indices.fielddata.breaker.NoneCircuitBreakerService;

public class ElasticClusterModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ClusterService.class).toProvider(Providers.of(null));
        bind(CircuitBreakerService.class).to(NoneCircuitBreakerService.class);
    }
}
