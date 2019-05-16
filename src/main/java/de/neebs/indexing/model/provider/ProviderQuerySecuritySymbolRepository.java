package de.neebs.indexing.model.provider;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderQuerySecuritySymbolRepository extends CrudRepository<ProviderQuerySecuritySymbol, ProviderQuerySecuritySymbolId> {
    Iterable<ProviderQuerySecuritySymbol> findByProviderId(int providerId);

    Iterable<ProviderQuerySecuritySymbol> findByProviderQueryId(int providerQueryId);
}
