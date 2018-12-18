package com.nttdata.dtl.model.provider;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface SharePriceRepository extends CrudRepository<SharePrice, SharePriceKey> {
    boolean existsByProviderQueryIdAndIsin(int providerQueryId, String isin);

    @Query("SELECT MAX(timestamp) FROM SharePrice WHERE providerQueryId = :providerQueryId AND isin = :isin")
    Date getLastQuote(@Param("providerQueryId") int providerQueryId, @Param("isin") String isin);
}
