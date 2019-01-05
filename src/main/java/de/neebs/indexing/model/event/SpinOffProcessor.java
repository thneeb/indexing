package de.neebs.indexing.model.event;

import de.neebs.indexing.model.index.IndexVariationProviderSecurity;
import de.neebs.indexing.model.index.IndexVariationProviderSecurityRepository;
import de.neebs.indexing.model.index.IndexVariationSecurity;
import de.neebs.indexing.model.index.IndexVariationTimespan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SpinOffProcessor implements EventProcessor {
    @Autowired
    private IndexVariationProviderSecurityRepository indexVariationProviderSecurityRepository;

    @Override
    public List<IndexVariationSecurity> execute(IndexVariationTimespan timespan, List<IndexVariationSecurity> securities, CorporateEvent ce) {
        Optional<IndexVariationSecurity> ivs = securities.stream().filter(f -> f.getIsin().equals(ce.getIsin())).findFirst();
        if (ivs.isPresent()) {
            SpinOff spinOff = (SpinOff)ce;

            IndexVariationProviderSecurity oldIvps = indexVariationProviderSecurityRepository.findActualProvider(
                    timespan.getVariationId(), spinOff.getIsin(), spinOff.getEffectiveDate()).iterator().next();
            IndexVariationProviderSecurity ivps = new IndexVariationProviderSecurity();
            ivps.setVariationId(timespan.getVariationId());
            ivps.setIsin(spinOff.getIsin());
            ivps.setProviderQueryId(spinOff.getSpinOffProviderQueryId());
            ivps.setSymbol(spinOff.getSpinOffSymbol());
            ivps.setQuality(spinOff.getSpinOffProviderQuality());
            ivps.setValidFrom(spinOff.getEffectiveDate());
            ivps.setValidTo(oldIvps.getValidTo());
            indexVariationProviderSecurityRepository.save(ivps);

            Double fraction = ivs.get().getFraction();
            fraction = fraction * (1 - spinOff.getSpinOffFraction());
            ivs.get().setFraction(fraction);
            IndexVariationSecurity newSecurity = new IndexVariationSecurity();
            newSecurity.setTimespanId(timespan.getTimespanId());
            newSecurity.setIsin(spinOff.getIsin());
            newSecurity.setFraction(spinOff.getSpinOffFraction());
            securities.add(newSecurity);
            return securities;
        } else {
            throw new IllegalStateException("No such security in this timespan");
        }
    }
}
