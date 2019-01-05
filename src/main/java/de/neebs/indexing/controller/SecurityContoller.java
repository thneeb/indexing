package de.neebs.indexing.controller;

import de.neebs.indexing.model.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createSecurity(@RequestBody Security entity) {
        if (securityRepository.existsById(entity.getIsin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        entity = securityRepository.save(entity);
        String reference = linkTo(methodOn(getClass()).findOneSecurity(entity.getIsin(), false)).withSelfRel().getHref();
        return ResponseEntity.created(URI.create(reference)).body(entity);
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

    @RequestMapping(value = "/{isim}/symbol", method = RequestMethod.POST)
    public ResponseEntity<?> createSymbol(@PathVariable String isim, @RequestBody SecuritySymbol entity) {
        if (entity.getIsin() != null && !entity.getIsin().equals(isim)) {
            return ResponseEntity.badRequest().build();
        }
        if (securitySymbolRepository.existsById(new SecuritySymbolId(isim, entity.getSymbol()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        entity.setIsin(isim);
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
