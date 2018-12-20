package com.nttdata.dtl.controller;

import com.nttdata.dtl.model.common.*;
import com.nttdata.dtl.model.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UpdateSharePrice {
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderQuerySecuritySymbolRepository providerQuerySecuritySymbolRepository;

    @Autowired
    private ProviderFactory providerFactory;

    @Autowired
    private ProviderQueryRepository providerQueryRepository;

    @Autowired
    private SecuritySymbolRepository securitySymbolRepository;

//    @Scheduled(cron="*/15 * * * * *")
    public void updateLastUpdated() {
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
                Optional<SecuritySymbol> optionalShare = securitySymbolRepository.findById(new SecuritySymbolId(pqs.get().getIsin(), pqs.get().getSymbol()));
                if (optionalProviderQuery.isPresent() && optionalShare.isPresent()) {
                    pqs.get().setLastRun(new Date());
                    providerQuerySecuritySymbolRepository.save(pqs.get());
                    tsp.execute(optionalProviderQuery.get(), optionalShare.get(), pqs.get().getLastRun());
                }
            }
        }
    }
}
