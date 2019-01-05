package de.neebs.indexing.model.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexVariationSecurityRepository extends CrudRepository<IndexVariationSecurity, IndexVariationSecurityId> {
    Iterable<IndexVariationSecurity> findByTimespanId(int timespanId);
}
