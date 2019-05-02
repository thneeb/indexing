package de.neebs.indexing.model.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndexMasterdataRepository extends CrudRepository<IndexMasterdata, Integer> {
    Optional<IndexMasterdata> findByName(String name);
}
