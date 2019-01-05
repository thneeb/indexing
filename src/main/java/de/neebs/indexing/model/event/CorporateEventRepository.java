package de.neebs.indexing.model.event;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CorporateEventRepository extends CrudRepository<AbstractCorporateEvent, Integer> {
    @Query("SELECT ce FROM AbstractCorporateEvent ce WHERE isin = :isin AND effectiveDate >= :validFrom AND effectiveDate < :validTo")
    Iterable<CorporateEvent> findEventsInTimespan(@Param("isin") String isin, @Param("validFrom") Date validFrom, @Param("validTo") Date validTo);
}
