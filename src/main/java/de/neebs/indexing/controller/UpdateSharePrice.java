package de.neebs.indexing.controller;

import de.neebs.indexing.model.common.Currency;
import de.neebs.indexing.model.common.CurrencyRepository;
import de.neebs.indexing.model.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UpdateSharePrice {
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderQueryRepository providerQueryRepository;

    @Autowired
    private ProviderFactory providerFactory;

    @Autowired
    private ProviderSymbolRepository providerSymbolRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ProviderQuerySecuritySymbolRepository providerQuerySecuritySymbolRepository;

    @Autowired
    private ProviderQueryExchangeRateRepository providerQueryExchangeRateRepository;

//    @Scheduled(cron="*/25 * * * * *")
    public void updateSecurityRate() {
        for (Provider provider : providerRepository.findAll()) {
            List<ProviderQuerySecuritySymbol> list = new ArrayList<>();
            providerQuerySecuritySymbolRepository.findByProviderId(provider.getProviderId()).forEach(list::add);
            Optional<ProviderQuerySecuritySymbol> pqs = list.stream().filter(f -> f.getLastRun() == null).findFirst();
            if (!pqs.isPresent()) {
                pqs = list.stream().filter(f -> f.getLastRun() != null).min(Comparator.comparing(ProviderQuerySecuritySymbol::getLastRun));
            }
            if (pqs.isPresent()) {
                TimeSeriesProvider tsp = providerFactory.getTimeSeriesProvider(provider.getName());
                Optional<ProviderQuery> optionalProviderQuery = providerQueryRepository.findById(pqs.get().getProviderQueryId());
                Optional<ProviderSymbol> optionalShare = providerSymbolRepository.findById(new ProviderSymbolId(provider.getProviderId(), pqs.get().getIsin(), pqs.get().getSymbol()));
                if (optionalProviderQuery.isPresent() && optionalShare.isPresent()) {
                    pqs.get().setLastRun(new Date());
                    providerQuerySecuritySymbolRepository.save(pqs.get());
                    tsp.execute(optionalProviderQuery.get(), optionalShare.get(), pqs.get().getLastRun());
                }
            }
        }
    }

    @Scheduled(cron="*/35 * * * * *")
    public void updateExchangeRate() {
        for (Provider provider : providerRepository.findAll()) {
            List<ProviderQueryExchangeRate> list = new ArrayList<>();
            providerQueryExchangeRateRepository.findByProviderId(provider.getProviderId()).forEach(list::add);
            Optional<ProviderQueryExchangeRate> pqe = list.stream().filter(f -> f.getLastRun() == null).findFirst();
            if (!pqe.isPresent()) {
                pqe = list.stream().filter(f -> f.getLastRun() != null).min(Comparator.comparing(ProviderQueryExchangeRate::getLastRun));
            }
            if (pqe.isPresent()) {
                TimeSeriesProvider tsp = providerFactory.getTimeSeriesProvider(provider.getName());
                Optional<ProviderQuery> optionalProviderQuery = providerQueryRepository.findById(pqe.get().getProviderQueryId());
                Optional<de.neebs.indexing.model.common.Currency> currencyFrom = currencyRepository.findById(pqe.get().getCurrencyFrom());
                Optional<Currency> currencyTo = currencyRepository.findById(pqe.get().getCurrencyTo());
                if (optionalProviderQuery.isPresent() && currencyFrom.isPresent() && currencyTo.isPresent()) {
                    pqe.get().setLastRun(new Date());
                    providerQueryExchangeRateRepository.save(pqe.get());
                    tsp.execute(optionalProviderQuery.get(), currencyFrom.get(), currencyTo.get(), pqe.get().getLastRun());
                }
            }
        }
    }
}
