package de.neebs.indexing.model.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndexVariationRepository extends CrudRepository<IndexVariation, Integer> {
    Iterable<IndexVariation> findByMasterdataId(int masterdataId);

    Optional<IndexVariation> findByMasterdataIdAndName(int masterdataId, String name);
}
