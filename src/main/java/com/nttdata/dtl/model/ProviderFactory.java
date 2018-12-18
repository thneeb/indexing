package com.nttdata.dtl.model;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ProviderFactory implements ApplicationContextAware {
    private Provider alphavantage = new Provider(1, "Alphavantage", 5);
    private ProviderQuery alphavantageDaily = new ProviderQuery(1, 1, "DAILY", ShareExchange.SHARE);
    private ProviderQuery alphavantageIntraday = new ProviderQuery(2, 1, "INTRADAY", ShareExchange.SHARE);
    private ApplicationContext context;

    public List<Provider> listProviders() {
        return Collections.singletonList(alphavantage);
    }

    public List<ProviderQuery> listProviderQueries() {
        return Arrays.asList(alphavantageDaily, alphavantageIntraday);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public TimeSeriesProvider getTimeSeriesProvider(String name) {
        Map<String, TimeSeriesProvider> providers = context.getBeansOfType(TimeSeriesProvider.class);
        for (TimeSeriesProvider entry : providers.values()) {
            if (entry.getName().equalsIgnoreCase(name)) {
                return entry;
            }
        }
        return null;
    }
}
