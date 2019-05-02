package de.neebs.indexing.model.event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DistributionRepository extends CrudRepository<Distribution, Integer> {
    Optional<Distribution> findByIsinAndEffectiveDate(String isin, Date effectiveDate);
}
