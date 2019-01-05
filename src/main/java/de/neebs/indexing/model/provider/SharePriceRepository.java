package de.neebs.indexing.model.provider;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface SharePriceRepository extends CrudRepository<SharePrice, SharePriceKey> {
    boolean existsByProviderQueryIdAndIsinAndSymbol(int providerQueryId, String isin, String symbol);

    @Query("SELECT MAX(timestamp) FROM SharePrice WHERE providerQueryId = :providerQueryId AND isin = :isin AND symbol = :symbol")
    Date getLastQuote(@Param("providerQueryId") int providerQueryId, @Param("isin") String isin, @Param("symbol") String symbol);

    Iterable<SharePrice> findByProviderQueryIdAndIsinAndSymbolOrderByTimestamp(int providerQueryId, String isin, String symbol);
}
