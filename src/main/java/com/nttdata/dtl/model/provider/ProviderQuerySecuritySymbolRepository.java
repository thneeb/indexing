package com.nttdata.dtl.model.provider;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProviderQuerySecuritySymbolRepository extends CrudRepository<ProviderQuerySecuritySymbol, ProviderQuerySecuritySymbolId> {
    @Query("SELECT pqs " +
            "FROM ProviderQuerySecuritySymbol pqs " +
            "JOIN ProviderQuery pq ON pqs.providerQueryId = pq.providerQueryId " +
            "WHERE pq.providerId = :providerId")
    Iterable<? extends ProviderQuerySecuritySymbol> findByProviderId(@Param("providerId") int providerId);
}
