package com.nttdata.dtl.model.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexMasterdataRepository extends CrudRepository<IndexMasterdata, Integer> {
}
