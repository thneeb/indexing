package de.neebs.indexing.model.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexVariationRepository extends CrudRepository<IndexVariation, Integer> {
    Iterable<IndexVariation> findByMasterdataId(int masterdataId);
}
