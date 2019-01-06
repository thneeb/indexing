package de.neebs.indexing.model.event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionRepository extends CrudRepository<Distribution, Integer> {
}
