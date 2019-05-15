package de.neebs.indexing.model.provider;

import de.neebs.indexing.model.common.Currency;

import java.util.Date;

public interface TimeSeriesProvider {
    String getName();
    void execute(ProviderQuery providerQuery, ProviderSymbol symbol, Date lastRun);
    boolean execute(ProviderQuery providerQuery, Currency fromCurrency, Currency toCurrency, Date lastRun);
}
