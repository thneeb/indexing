package com.nttdata.dtl.controller;

import com.nttdata.dtl.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/stockprice")
public class StockPriceController {
    private static final Logger LOG = LoggerFactory.getLogger(StockPriceController.class);

    @Autowired
    private ShareFactory shareFactory;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ProviderFactory providerFactory;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderQueryRepository providerQueryRepository;

    @Autowired
    private ProviderQueryShareRepository providerQueryShareRepository;

    @RequestMapping("/shares")
    public ResponseEntity<?> createShares() {
        List<Share> shares = shareFactory.listShares();
        Set<Currency> currencies = shares.stream().map(f -> new Currency(f.getCurrency())).collect(Collectors.toSet());
        currencyRepository.saveAll(currencies);
        shareRepository.saveAll(shares);
        return ResponseEntity.ok("success");
    }

    @RequestMapping("/providers")
    public ResponseEntity<?> createProviders() {
        List<Provider> providers = providerFactory.listProviders();
        List<ProviderQuery> providerQueries = providerFactory.listProviderQueries();
        providerRepository.saveAll(providers);
        providerQueryRepository.saveAll(providerQueries);
        return ResponseEntity.ok("success");
    }

    @RequestMapping("/retrieve")
    public ResponseEntity<?> retrievePrices(@RequestParam String provider, @RequestParam String query, @RequestParam String isin) {
        Optional<Share> share = shareRepository.findById(isin);
        if (!share.isPresent()) {
            throw new IllegalArgumentException("Cannot find isin: " + isin);
        }
        List<Provider> providers = providerRepository.findByName(provider);
        for (Provider p : providers) {
            for (ProviderQuery pq : providerQueryRepository.findByProviderIdAndName(p.getProviderId(), query)) {
                TimeSeriesProvider tsp = providerFactory.getTimeSeriesProvider(provider);
                if (tsp != null) {
                    tsp.execute(pq, share.get(), null);
                }
            }
        }
        return ResponseEntity.ok("success");
    }

    @RequestMapping("/init")
    public ResponseEntity<?> initialize() {
        try {
            for (Provider provider : providerRepository.findAll()) {
                TimeSeriesProvider tsp = providerFactory.getTimeSeriesProvider(provider.getName());
                if (tsp != null) {
                    for (ProviderQuery providerQuery : providerQueryRepository.findByProviderId(provider.getProviderId())) {
                        for (Share share : tsp.availableShares()) {
                            Optional<ProviderQueryShare> optPqs = providerQueryShareRepository.findById(
                                    new ProviderQueryShareId(providerQuery.getProviderQueryId(), share.getIsin()));
                            ProviderQueryShare pqs;
                            pqs = optPqs.orElseGet(() -> new ProviderQueryShare(
                                    providerQuery.getProviderQueryId(), share.getIsin(), -1, null));
                            if (tsp.execute(providerQuery, share, pqs.getLastRun())) {
                                pqs.setLastRun(new Date());
                                providerQueryShareRepository.save(pqs);
                                LOG.info("Retrieved time series from " + provider.getName() + " " + providerQuery.getName() + " for " + share.getSymbol());
                                sleep(13000);
                            }
                        }
                    }
                }
            }
            return ResponseEntity.ok("success");
        } catch (InterruptedException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
