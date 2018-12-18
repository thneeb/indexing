package com.nttdata.dtl.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProviderQueryRepository extends CrudRepository<ProviderQuery, Integer> {
    Iterable<? extends ProviderQuery> findByProviderIdAndName(int providerId, String name);

    Iterable<? extends ProviderQuery> findByProviderId(int providerId);
}
