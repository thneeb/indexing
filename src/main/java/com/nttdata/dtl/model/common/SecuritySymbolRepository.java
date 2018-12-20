package com.nttdata.dtl.model.common;

import org.springframework.data.repository.CrudRepository;

public interface SecuritySymbolRepository extends CrudRepository<SecuritySymbol, SecuritySymbolId> {
    Iterable<? extends SecuritySymbol> findByIsin(String isin);
}
