package de.neebs.indexing.controller;

import de.neebs.indexing.model.common.Security;
import de.neebs.indexing.model.common.SecurityRepository;
import de.neebs.indexing.model.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderQueryRepository providerQueryRepository;

    @Autowired
    private SharePriceRepository sharePriceRepository;

    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private ProviderSymbolRepository providerSymbolRepository;

    @Autowired
    private ProviderQuerySecuritySymbolRepository providerQuerySecuritySymbolRepository;

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

    @RequestMapping(value = "/{provider}/security/{isin}", method = RequestMethod.POST)
    public ResponseEntity<?> addSymbol(@PathVariable String provider, @PathVariable String isin, @RequestBody ProviderSymbol symbol) {
        Optional<Provider> optionalProvider = getProvider(provider);
        if (!optionalProvider.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        symbol.setProviderId(optionalProvider.get().getProviderId());
        Optional<Security> optionalSecurity = securityRepository.findById(isin);
        if (!optionalSecurity.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        symbol.setIsin(optionalSecurity.get().getIsin());
        symbol = providerSymbolRepository.save(symbol);
        String reference = linkTo(methodOn(getClass()).findSymbol(provider, isin, symbol.getSymbol())).withSelfRel().getHref();
        return ResponseEntity.created(URI.create(reference)).body(symbol);
    }

    @RequestMapping(value = "/{provider}/security/{isin}", method = RequestMethod.GET)
    public ResponseEntity<?> findSymbols(@PathVariable String provider, @PathVariable String isin) {
        Optional<Provider> optionalProvider = getProvider(provider);
        if (!optionalProvider.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<ProviderSymbol> list = new ArrayList<>();
        providerSymbolRepository.findByProviderIdAndIsin(optionalProvider.get().getProviderId(), isin).forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/{provider}/security/{isin}/{symbol}", method = RequestMethod.GET)
    public ResponseEntity<?> findSymbol(@PathVariable String provider, @PathVariable String isin, @PathVariable String symbol) {
        Optional<Provider> optionalProvider = getProvider(provider);
        if (!optionalProvider.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Optional<ProviderSymbol> optionalProviderSymbol = providerSymbolRepository.findById(new ProviderSymbolId(optionalProvider.get().getProviderId(), isin, symbol));
        if (optionalProviderSymbol.isPresent()) {
            return ResponseEntity.ok(optionalProviderSymbol.get());
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
    @RequestMapping(value = "/{provider}/query/{query}/security/{isin}/{symbol}", method = RequestMethod.POST)
    public ResponseEntity<?> addSymbolToQuery(@PathVariable String provider, @PathVariable String query, @PathVariable String isin, @PathVariable String symbol, @RequestBody ProviderQuerySecuritySymbol entity) {
        Optional<ProviderQuery> optionalProviderQuery = getQuery(provider, query);
        if (optionalProviderQuery.isPresent()) {
            entity.setProviderId(optionalProviderQuery.get().getProviderId());
            entity.setProviderQueryId(optionalProviderQuery.get().getProviderQueryId());
            entity.setIsin(isin);
            entity.setSymbol(symbol);
            entity = providerQuerySecuritySymbolRepository.save(entity);
            String reference = linkTo(methodOn(getClass()).findQuerySymbol(provider, query, isin, symbol)).withSelfRel().getHref();
            return ResponseEntity.created(URI.create(reference)).body(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{provider}/query/{query}/security", method = RequestMethod.GET)
    public ResponseEntity<?> findSecuritiesForQuery(@PathVariable String provider, @PathVariable String query) {
        Optional<ProviderQuery> optionalProviderQuery = getQuery(provider, query);
        if (optionalProviderQuery.isPresent()) {
            List<ProviderQuerySecuritySymbol> list = new ArrayList<>();
            providerQuerySecuritySymbolRepository.findByProviderQueryId(optionalProviderQuery.get().getProviderQueryId()).forEach(list::add);
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{provider}/query/{query}/security/{isin}/{symbol}", method = RequestMethod.GET)
    public ResponseEntity<?> findQuerySymbol(@PathVariable String provider, @PathVariable String query, @PathVariable String isin, @PathVariable String symbol) {
        Optional<ProviderQuery> optionalProviderQuery = getQuery(provider, query);
        if (optionalProviderQuery.isPresent()) {
            Optional<ProviderQuerySecuritySymbol> optionalProviderQuerySecuritySymbol = providerQuerySecuritySymbolRepository.findById(new ProviderQuerySecuritySymbolId(optionalProviderQuery.get().getProviderQueryId(), isin, symbol));
            if (optionalProviderQuerySecuritySymbol.isPresent()) {
                return ResponseEntity.ok(optionalProviderQuerySecuritySymbol.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{provider}/query/{query}/security/{isin}/{symbol}/price", method = RequestMethod.GET)
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
