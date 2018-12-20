package com.nttdata.dtl.model.provider;

import com.nttdata.dtl.model.common.Currency;
import com.nttdata.dtl.model.common.SecuritySymbol;

import java.util.Date;

public interface TimeSeriesProvider {
    String getName();
    void execute(ProviderQuery providerQuery, SecuritySymbol symbol, Date lastRun);
    boolean execute(ProviderQuery providerQuery, Currency fromCurrency, Currency toCurrency, Date lastRun);
}
