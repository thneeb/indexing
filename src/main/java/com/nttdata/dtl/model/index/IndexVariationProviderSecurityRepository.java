package com.nttdata.dtl.model.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexVariationProviderSecurityRepository extends CrudRepository<IndexVariationProviderSecurity, Integer> {
    Iterable<IndexVariationProviderSecurity> findByVariationIdAndIsinOrderByValidFrom(Integer variationId, String isin);
}