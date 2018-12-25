package com.nttdata.dtl.model.provider;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderQueryExchangeRateRepository extends CrudRepository<ProviderQueryExchangeRate, ProviderQueryExchangeRateId> {
    @Query("SELECT pqe " +
            "FROM ProviderQueryExchangeRate pqe " +
            "JOIN ProviderQuery pq ON pqe.providerQueryId = pq.providerQueryId " +
            "WHERE pq.providerId = :providerId")
    Iterable<ProviderQueryExchangeRate> findByProviderId(@Param("providerId") int providerId);
}
