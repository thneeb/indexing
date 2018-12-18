package com.nttdata.dtl.model.provider;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {
    List<Provider> findByName(String name);
}
