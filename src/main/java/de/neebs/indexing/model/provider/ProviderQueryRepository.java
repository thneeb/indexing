package de.neebs.indexing.model.provider;

import org.springframework.data.repository.CrudRepository;

public interface ProviderQueryRepository extends CrudRepository<ProviderQuery, Integer> {
    Iterable<? extends ProviderQuery> findByProviderIdAndName(int providerId, String name);

    Iterable<? extends ProviderQuery> findByProviderId(int providerId);
}
