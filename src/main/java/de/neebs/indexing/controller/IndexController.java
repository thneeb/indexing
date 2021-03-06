package de.neebs.indexing.controller;

import de.neebs.indexing.model.event.EventConfig;
import de.neebs.indexing.model.event.EventFacade;
import de.neebs.indexing.model.index.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.PrivilegedActionException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private IndexMasterdataRepository indexMasterdataRepository;

    @Autowired
    private IndexVariationRepository indexVariationRepository;

    @Autowired
    private IndexVariationTimespanRepository indexVariationTimespanRepository;

    @Autowired
    private IndexVariationSecurityRepository indexVariationSecurityRepository;

    @Autowired
    private IndexVariationProviderSecurityRepository indexVariationProviderSecurityRepository;

    @Autowired
    private EventFacade eventFacade;

    @Autowired
    private IndexFacade indexFacade;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createIndex(@RequestBody IndexMasterdata entity) {
        try {
            entity.setMasterdataId(null);
            entity = indexMasterdataRepository.save(entity);
            String reference = linkTo(methodOn(getClass()).findIndex(entity.getMasterdataId())).withSelfRel().getHref();
            return ResponseEntity.created(URI.create(reference)).body(entity);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{masterdataId}", method = RequestMethod.GET)
    public ResponseEntity<?> findIndex(@PathVariable int masterdataId) {
        Optional<IndexMasterdata> optionalIndexMasterdata = indexMasterdataRepository.findById(masterdataId);
        if (optionalIndexMasterdata.isPresent()) {
            return ResponseEntity.ok(optionalIndexMasterdata.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<IndexMasterdata>> findIndexes() {
        List<IndexMasterdata> list = new ArrayList<>();
        indexMasterdataRepository.findAll().forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/{masterdataId}/variation", method = RequestMethod.POST)
    public ResponseEntity<?> createVariation(@PathVariable int masterdataId, @RequestBody IndexVariation entity) {
        try {
            entity.setVariationId(null);
            if (entity.getMasterdataId() != null && entity.getMasterdataId() != masterdataId) {
                return ResponseEntity.badRequest().build();
            }
            entity.setMasterdataId(masterdataId);
            entity = indexVariationRepository.save(entity);
            String reference = linkTo(methodOn(getClass()).findVariation(entity.getMasterdataId(), entity.getVariationId())).withSelfRel().getHref();
            return ResponseEntity.created(URI.create(reference)).body(entity);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}", method = RequestMethod.GET)
    public ResponseEntity<?> findVariation(@PathVariable int masterdataId, @PathVariable int variationId) {
        Optional<IndexVariation> optionalIndexVariation = indexVariationRepository.findById(variationId);
        if (optionalIndexVariation.isPresent()) {
            if (optionalIndexVariation.get().getMasterdataId() == masterdataId) {
                return ResponseEntity.ok(optionalIndexVariation.get());
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{masterdataId}/variation", method = RequestMethod.GET)
    public ResponseEntity<List<IndexVariation>> findVariations(@PathVariable int masterdataId) {
        List<IndexVariation> list = new ArrayList<>();
        indexVariationRepository.findByMasterdataId(masterdataId).forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/events", method = RequestMethod.POST)
    public ResponseEntity<?> applyEvents(@PathVariable int masterdataId, @PathVariable int variationId, @RequestBody EventConfig config) {
        Optional<IndexVariation> optionalIndexVariation = indexVariationRepository.findById(variationId);
        if (!optionalIndexVariation.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        if (optionalIndexVariation.get().getMasterdataId() != masterdataId) {
            return ResponseEntity.badRequest().build();
        }
        eventFacade.applyEvents(optionalIndexVariation.get(), config.getFromDate(), config.getToDate());
        return ResponseEntity.ok("success");
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/timespan", method = RequestMethod.POST)
    public ResponseEntity<?> createTimespan(@PathVariable int masterdataId, @PathVariable int variationId,
                                            @RequestBody TimespanWithSecurity entity) {
        try {
            Optional<IndexVariation> optionalIndexVariation = indexVariationRepository.findById(variationId);
            if (!optionalIndexVariation.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            if (optionalIndexVariation.get().getMasterdataId() != masterdataId) {
                return ResponseEntity.badRequest().build();
            }
            if (entity.getSecurities() != null) {
                if (entity.getSecurities().stream().anyMatch(f -> f.getFraction() == null)) {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
            IndexVariationTimespan timespan = new IndexVariationTimespan();
            timespan.setVariationId(variationId);
            timespan.setValidFrom(entity.getValidFrom());
            timespan.setValidTo(entity.getValidTo());
            List<IndexVariationSecurity> list = new ArrayList<>();
            for (SecurityDistribution sd : entity.getSecurities()) {
                IndexVariationSecurity ivs = new IndexVariationSecurity();
                ivs.setIsin(sd.getIsin());
                ivs.setFraction(sd.getFraction());
                list.add(ivs);
            }
            timespan = indexFacade.insertTimespan(timespan, list, entity.isForce());
            String reference = linkTo(methodOn(getClass()).findTimespan(optionalIndexVariation.get().getMasterdataId(),
                    timespan.getVariationId(), timespan.getTimespanId())).withSelfRel().getHref();
            entity.setTimespanId(timespan.getTimespanId());
            return ResponseEntity.created(URI.create(reference)).body(entity);
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (PrivilegedActionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/timespan", method = RequestMethod.GET)
    public ResponseEntity<?> findTimespans(@PathVariable int masterdataId, @PathVariable int variationId, @RequestParam(required = false) String timestamp) {
        Date dtTimestamp;
        if (timestamp != null) {
            try {
                dtTimestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(timestamp);
            } catch (ParseException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            dtTimestamp = null;
        }
        Optional<IndexVariation> optionalIndexVariation = indexVariationRepository.findById(variationId);
        if (!optionalIndexVariation.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        if (optionalIndexVariation.get().getMasterdataId() != masterdataId) {
            return ResponseEntity.badRequest().build();
        }
        List<IndexVariationTimespan> list = new ArrayList<>();
        indexVariationTimespanRepository.findAll().forEach(list::add);
        if (dtTimestamp != null) {
            list = list.stream().filter(f -> !f.getValidFrom().after(dtTimestamp) && f.getValidTo().after(dtTimestamp)).collect(Collectors.toList());
        }
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/timespan/{timespanId}", method = RequestMethod.GET)
    public ResponseEntity<?> findTimespan(@PathVariable int masterdataId, @PathVariable int variationId, @PathVariable int timespanId) {
        Optional<IndexVariationTimespan> optionalIndexVariationTimespan = indexVariationTimespanRepository.findById(timespanId);
        if (optionalIndexVariationTimespan.isPresent()) {
            if (optionalIndexVariationTimespan.get().getVariationId() != variationId) {
                return ResponseEntity.badRequest().build();
            }
            Optional<IndexVariation> optionalIndexVariation = indexVariationRepository.findById(variationId);
            if (!optionalIndexVariation.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            if (optionalIndexVariation.get().getMasterdataId() != masterdataId) {
                return ResponseEntity.badRequest().build();
            }
            List<IndexVariationSecurity> list = new ArrayList<>();
            indexVariationSecurityRepository.findByTimespanId(timespanId).forEach(list::add);
            TimespanWithSecurity ts = new TimespanWithSecurity();
            ts.setValidFrom(optionalIndexVariationTimespan.get().getValidFrom());
            ts.setValidTo(optionalIndexVariationTimespan.get().getValidTo());
            ts.setSecurities(list.stream().map(f -> new SecurityDistribution(f.getIsin(), f.getFraction())).collect(Collectors.toList()));
            return ResponseEntity.ok(ts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/security", method = RequestMethod.GET)
    public ResponseEntity<?> findProviderSecurity(@PathVariable int masterdataId, @PathVariable int variationId) {
        Optional<IndexVariation> optionalIndexVariation = indexVariationRepository.findById(variationId);
        if (!optionalIndexVariation.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        if (optionalIndexVariation.get().getMasterdataId() != masterdataId) {
            return ResponseEntity.badRequest().build();
        }
        List<IndexVariationProviderSecurity> list = new ArrayList<>();
        indexVariationProviderSecurityRepository.findByVariationId(optionalIndexVariation.get().getVariationId()).forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/security", method = RequestMethod.POST)
    public ResponseEntity<?> createProviderSecurity(@PathVariable int masterdataId, @PathVariable int variationId,
                                            @RequestBody IndexVariationProviderSecurity entity) {
        try {
            if (entity.getVariationId() != null && entity.getVariationId() != variationId) {
                return ResponseEntity.badRequest().build();
            }
            entity.setVariationId(variationId);
            Optional<IndexVariation> v = indexVariationRepository.findById(variationId);
            if (!v.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            if (v.get().getMasterdataId() != masterdataId) {
                return ResponseEntity.badRequest().build();
            }
            entity = indexFacade.insertProviderSecurity(entity, false);
            String reference = linkTo(methodOn(getClass()).findProviderSecurity(masterdataId, variationId, entity.getSymbolId())).withSelfRel().getHref();
            return ResponseEntity.created(URI.create(reference)).body(entity);
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (PrivilegedActionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/security/{securityId}", method = RequestMethod.GET)
    public ResponseEntity<?> findProviderSecurity(@PathVariable int masterdataId, @PathVariable int variationId, @PathVariable int securityId) {
        Optional<IndexVariationProviderSecurity> optionalIndexVariationProvider = indexVariationProviderSecurityRepository.findById(securityId);
        if (!optionalIndexVariationProvider.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalIndexVariationProvider.get().getVariationId() != variationId) {
            return ResponseEntity.notFound().build();
        }
        Optional<IndexVariation> optionalIndexVariation = indexVariationRepository.findById(variationId);
        if (!optionalIndexVariation.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalIndexVariation.get().getMasterdataId() != masterdataId) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalIndexVariationProvider.get());
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/exchange", method = RequestMethod.POST)
    public ResponseEntity<?> createProviderExchange(@PathVariable int masterdataId, @PathVariable int variationId,
                                                    @RequestBody IndexVariationProviderExchange entity) {
        try {
            if (entity.getVariationId() != null && entity.getVariationId() != variationId) {
                return ResponseEntity.badRequest().build();
            }
            entity.setVariationId(variationId);
            Optional<IndexVariation> v = indexVariationRepository.findById(variationId);
            if (!v.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            if (v.get().getMasterdataId() != masterdataId) {
                return ResponseEntity.badRequest().build();
            }
            entity = indexFacade.insertProviderExchange(entity, false);
            String reference = linkTo(methodOn(getClass()).findProviderExchange(masterdataId, variationId, entity.getProviderExchangeId())).withSelfRel().getHref();
            return ResponseEntity.created(URI.create(reference)).body(entity);
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (PrivilegedActionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/exchange/{exchangeId}", method = RequestMethod.GET)
    public ResponseEntity<?> findProviderExchange(@PathVariable int masterdataId, @PathVariable int variationId, @PathVariable int exchangeId) {
        Optional<IndexVariationProviderSecurity> optionalIndexVariationProvider = indexVariationProviderSecurityRepository.findById(exchangeId);
        if (!optionalIndexVariationProvider.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalIndexVariationProvider.get().getVariationId() != variationId) {
            return ResponseEntity.notFound().build();
        }
        Optional<IndexVariation> optionalIndexVariation = indexVariationRepository.findById(variationId);
        if (!optionalIndexVariation.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalIndexVariation.get().getMasterdataId() != masterdataId) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalIndexVariationProvider.get());
    }
    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/verficiation", method = RequestMethod.POST)
    public ResponseEntity<?> verifyVariation(@PathVariable int masterdataId, @PathVariable int variationId, @RequestBody CalculationConfig calculationConfig) {
        return ResponseEntity.ok("success");
    }

    @RequestMapping(value = "/{masterdataId}/variation/{variationId}/calculation", method = RequestMethod.POST)
    public ResponseEntity<?> calculateVariation(@PathVariable int masterdataId, @PathVariable int variationId, @RequestBody CalculationConfig calculationConfig) {
        Optional<IndexVariation> v = indexVariationRepository.findById(variationId);
        if (!v.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (v.get().getMasterdataId() != masterdataId) {
            return ResponseEntity.notFound().build();
        }
        indexFacade.calculateVariation(variationId, calculationConfig);
        return ResponseEntity.ok("success");
    }


    private static class SecurityDistribution {
        private String isin;
        private Double fraction;

        public SecurityDistribution() {
        }

        public SecurityDistribution(String isin, Double fraction) {
            this.isin = isin;
            this.fraction = fraction;
        }

        public String getIsin() {
            return isin;
        }

        public void setIsin(String isin) {
            this.isin = isin;
        }

        public Double getFraction() {
            return fraction;
        }

        public void setFraction(Double fraction) {
            this.fraction = fraction;
        }
    }

    private static class TimespanWithSecurity {
        private boolean force;
        private Date validFrom;
        private Date validTo;
        private List<SecurityDistribution> securities = new ArrayList<>();
        private Integer timespanId;

        public boolean isForce() {
            return force;
        }

        public void setForce(boolean force) {
            this.force = force;
        }

        public Date getValidFrom() {
            return validFrom;
        }

        public void setValidFrom(Date validFrom) {
            this.validFrom = validFrom;
        }

        public Date getValidTo() {
            return validTo;
        }

        public void setValidTo(Date validTo) {
            this.validTo = validTo;
        }

        public List<SecurityDistribution> getSecurities() {
            return securities;
        }

        public void setSecurities(List<SecurityDistribution> securities) {
            this.securities = securities;
        }

        public void setTimespanId(Integer timespanId) {
            this.timespanId = timespanId;
        }

        public Integer getTimespanId() {
            return timespanId;
        }
    }
}
