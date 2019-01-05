package de.neebs.indexing.model.index;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IndexVariationTimespanRepository extends CrudRepository<IndexVariationTimespan, Integer> {
    Iterable<IndexVariationTimespan> findByVariationIdOrderByValidFrom(int variationId);

    @Query("SELECT ivt FROM IndexVariationTimespan ivt WHERE variationId = :variationId AND validFrom <= :timestamp AND validTo > :timestamp")
    Iterable<IndexVariationTimespan> findByVariationIdAndDate(@Param("variationId") int variationId, @Param("timestamp") Date timestamp);
}
