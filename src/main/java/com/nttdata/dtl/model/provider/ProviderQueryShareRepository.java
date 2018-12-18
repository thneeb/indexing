package com.nttdata.dtl.model.provider;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ProviderQueryShareRepository extends CrudRepository<ProviderQueryShare, ProviderQueryShareId> {
    @Query("SELECT pqs " +
            "FROM ProviderQueryShare pqs " +
            "JOIN ProviderQuery pq ON pqs.providerQueryId = pq.providerQueryId " +
            "WHERE pq.providerQueryId = :providerId " +
            "AND pqs.lastRun >= :timestamp")
    Iterable<? extends ProviderQueryShare> findByProviderIdAndLastRunGreaterThan(
            @Param("providerId") int providerId, @Param("timestamp") Date timestamp);
}
