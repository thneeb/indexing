package de.neebs.indexing.model.provider;

import org.springframework.data.repository.CrudRepository;

public interface ProviderSymbolRepository extends CrudRepository<ProviderSymbol, ProviderSymbolId> {
    Iterable<ProviderSymbol> findByProviderIdAndIsin(int providerId, String isin);
}
