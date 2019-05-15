package de.neebs.indexing.controller;

import de.neebs.indexing.model.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderQueryRepository providerQueryRepository;

    @Autowired
    private SharePriceRepository sharePriceRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Provider>> findProviders() {
        List<Provider> list = new ArrayList<>();
        providerRepository.findAll().forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/{provider}", method = RequestMethod.GET)
    public ResponseEntity<?> findProvider(@PathVariable String provider) {
        Optional<Provider> optionalProvider = getProvider(provider);
        if (optionalProvider.isPresent()) {
            return ResponseEntity.ok(optionalProvider.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{provider}/query", method = RequestMethod.GET)
    public ResponseEntity<List<ProviderQuery>> findQueries(@PathVariable String provider) {
        Optional<Provider> optionalProvider = getProvider(provider);
        if (optionalProvider.isPresent()) {
            List<ProviderQuery> list = new ArrayList<>();
            providerQueryRepository.findByProviderId(optionalProvider.get().getProviderId()).forEach(list::add);
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{provider}/query/{query}", method = RequestMethod.GET)
    public ResponseEntity<?> findQuery(@PathVariable String provider, @PathVariable String query) {
        Optional<ProviderQuery> optionalProviderQuery = getQuery(provider, query);
        if (optionalProviderQuery.isPresent()) {
            return ResponseEntity.ok(optionalProviderQuery.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{provider}/query/{query}/security/{isin}/{symbol}", method = RequestMethod.GET)
    public ResponseEntity<?> findSharePrices(@PathVariable String provider, @PathVariable String query, @PathVariable String isin, @PathVariable String symbol) {
        Optional<ProviderQuery> optionalProviderQuery = getQuery(provider, query);
        if (optionalProviderQuery.isPresent()) {
            List<SharePrice> list = new ArrayList<>();
            sharePriceRepository.findByProviderQueryIdAndIsinAndSymbolOrderByTimestamp(
                    optionalProviderQuery.get().getProviderQueryId(), isin, symbol).forEach(list::add);
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Optional<ProviderQuery> getQuery(String provider, String query) {
        Optional<Provider> optionalProvider = getProvider(provider);
        if (optionalProvider.isPresent()) {
            try {
                int queryId = Integer.parseInt(query);
                Optional<ProviderQuery> optionalProviderQuery = providerQueryRepository.findById(queryId);
                if (optionalProviderQuery.isPresent() && optionalProviderQuery.get().getProviderId() != optionalProvider.get().getProviderId()) {
                    return Optional.empty();
                }
                return optionalProviderQuery;
            } catch (NumberFormatException e) {
                List<ProviderQuery> list = new ArrayList<>();
                providerQueryRepository.findByProviderIdAndName(optionalProvider.get().getProviderId(), query).forEach(list::add);
                if (list.isEmpty()) {
                    return Optional.empty();
                } else {
                    return Optional.of(list.get(0));
                }
            }
        } else {
            return Optional.empty();
        }
    }

    private Optional<Provider> getProvider(String name) {
        try {
            int id = Integer.parseInt(name);
            return providerRepository.findById(id);
        } catch (NumberFormatException e) {
            List<Provider> list = providerRepository.findByName(name);
            if (list.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(list.get(0));
            }
        }
    }
}
