package de.neebs.indexing.model.common;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, String> {
    List<Currency> findAll();
}
