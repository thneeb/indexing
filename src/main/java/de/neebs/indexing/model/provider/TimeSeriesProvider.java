package de.neebs.indexing.model.provider;

import de.neebs.indexing.model.common.Currency;
import de.neebs.indexing.model.common.SecuritySymbol;

import java.util.Date;

public interface TimeSeriesProvider {
    String getName();
    void execute(ProviderQuery providerQuery, SecuritySymbol symbol, Date lastRun);
    boolean execute(ProviderQuery providerQuery, Currency fromCurrency, Currency toCurrency, Date lastRun);
}
