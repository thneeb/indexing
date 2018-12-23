package com.nttdata.dtl.model.index;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexVariationProviderExchangeRepository extends CrudRepository<IndexVariationProviderExchange, Integer> {
    Iterable<IndexVariationProviderExchange> findByVariationIdAndCurrencyFromAndCurrencyToOrderByValidFrom(int variationId, String currencyFrom, String currencyTo);
}
