package de.neebs.indexing.model.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexEventProcessorRepository extends CrudRepository<IndexEventProcessor, IndexEventProcessorId> {
    Iterable<IndexEventProcessor> findByMasterdataId(int masterdataId);
}
