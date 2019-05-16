package de.neebs.indexing.model.index;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IndexVariationProviderSecurityRepository extends CrudRepository<IndexVariationProviderSecurity, Integer> {
    Iterable<IndexVariationProviderSecurity> findByVariationIdAndIsinOrderByValidFrom(Integer variationId, String isin);

    @Query("SELECT ivps " +
            "FROM IndexVariationProviderSecurity ivps " +
            "WHERE variationId = :variationId " +
            "AND isin = :isin " +
            "AND validFrom <= :timestamp AND validTo > :timestamp " +
            "ORDER BY quality")
    Iterable<IndexVariationProviderSecurity> findActualProvider(@Param("variationId") int variationId, @Param("isin") String isin, @Param("timestamp") Date timestamp);

    Iterable<IndexVariationProviderSecurity> findByVariationId(int variationId);
}
