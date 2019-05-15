package de.neebs.indexing.controller;

import de.neebs.indexing.model.common.*;
import de.neebs.indexing.model.event.Distribution;
import de.neebs.indexing.model.event.DistributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/security")
public class SecurityContoller {
    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private SecuritySymbolRepository securitySymbolRepository;

    @Autowired
    private DistributionRepository distributionRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createSecurity(@RequestBody Security entity) {
        if (securityRepository.existsById(entity.getIsin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        entity = securityRepository.save(entity);
        String reference = linkTo(methodOn(getClass()).findOneSecurity(entity.getIsin(), false)).withSelfRel().getHref();
        return ResponseEntity.created(URI.create(reference)).body(entity);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findSecurities(@RequestParam(required = false) boolean symbols) {
        List<Security> list = new ArrayList<>();
        securityRepository.findAll().forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/{isin}", method = RequestMethod.GET)
    public ResponseEntity<?> findOneSecurity(@PathVariable String isin, @RequestParam(required = false) boolean symbols) {
        Optional<Security> security = securityRepository.findById(isin);
        if (security.isPresent()) {
            if (symbols) {
                List<SecuritySymbol> list = new ArrayList<>();
                securitySymbolRepository.findByIsin(isin).forEach(list::add);
                return ResponseEntity.ok(new SecurityWithSymbols(security.get(), list));
            } else {
                return ResponseEntity.ok(security.get());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{isin}/symbol", method = RequestMethod.POST)
    public ResponseEntity<?> createSymbol(@PathVariable String isin, @RequestBody SecuritySymbol entity) {
        if (entity.getIsin() != null && !entity.getIsin().equals(isin)) {
            return ResponseEntity.badRequest().build();
        }
        if (securitySymbolRepository.existsById(new SecuritySymbolId(isin, entity.getSymbol()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        entity.setIsin(isin);
        entity = securitySymbolRepository.save(entity);
        String reference = linkTo(methodOn(getClass()).findOneSymbol(entity.getIsin(), entity.getSymbol())).withSelfRel().getHref();
        return ResponseEntity.created(URI.create(reference)).body(entity);
    }

    @RequestMapping(value = "/{isin}/symbol/{symbol}", method = RequestMethod.GET)
    public ResponseEntity<?> findOneSymbol(@PathVariable String isin, @PathVariable String symbol) {
        Optional<SecuritySymbol> securitySymbol = securitySymbolRepository.findById(new SecuritySymbolId(isin, symbol));
        if (securitySymbol.isPresent()) {
            return ResponseEntity.ok(securitySymbol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{isin}/distribution", method = RequestMethod.POST)
    public ResponseEntity<Distribution> createDistribution(@PathVariable String isin, @RequestBody Distribution entity) {
        entity.setEventId(null);
        entity.setIsin(isin);
        entity = distributionRepository.save(entity);
        return ResponseEntity.created(URI.create(linkTo(methodOn(getClass()).findDistribution(isin, entity.getEventId())).withSelfRel().getHref())).body(entity);
    }

    @RequestMapping(value = "/{isin}/distribution/{eventId}", method = RequestMethod.GET)
    public ResponseEntity<?> findDistribution(@PathVariable String isin, @PathVariable int eventId) {
        Optional<Distribution> distribution = distributionRepository.findById(eventId);
        if (distribution.isPresent()) {
            if (distribution.get().getIsin().equals(isin)) {
                return ResponseEntity.ok(distribution.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{isin}/distribution", method = RequestMethod.GET)
    public ResponseEntity<?> findDistributionByDate(@PathVariable String isin, @RequestParam String date) {
        try {
            Date effectiveDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            Optional<Distribution> distribution = distributionRepository.findByIsinAndEffectiveDate(isin, effectiveDate);
            if (distribution.isPresent()) {
                return ResponseEntity.ok(distribution);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private class SecurityWithSymbols extends Security {
        private final List<SecuritySymbol> symbols;

        SecurityWithSymbols(Security security, List<SecuritySymbol> symbols) {
            super(security);
            this.symbols = symbols;
        }

        public List<SecuritySymbol> getSymbols() {
            return symbols;
        }
    }
}
