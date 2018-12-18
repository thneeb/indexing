package com.nttdata.dtl.model;

import java.util.Date;
import java.util.List;

public interface TimeSeriesProvider {
    String getName();
    boolean execute(ProviderQuery providerQuery, Share share, Date lastRun);
    List<Share> availableShares();
    boolean execute(ProviderQuery providerQuery, Currency fromCurrency, Currency toCurrency, Date lastRun);
}
