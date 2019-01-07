package de.neebs.indexing.model.event;

import de.neebs.indexing.model.index.IndexVariationSecurity;
import de.neebs.indexing.model.index.IndexVariationTimespan;
import de.neebs.indexing.model.provider.SharePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Der ausgeschüttete Betrag einer Aktie wird vollständig wieder in die Aktie
 * investiert. Dazu wird der Preis der Aktie vor der Ausschüttung ins
 * Verhältnis mit dem Ausschüttungsbetrag gesetzt und entsprechend die Anzahl
 * der Aktien im Index erhöht.
 */
@Component
public class DistributionReinvestProcessor implements EventProcessor {
    @Autowired
    private EventHelper eventHelper;

    @Override
    public List<IndexVariationSecurity> execute(IndexVariationTimespan timespan, List<IndexVariationSecurity> securities, CorporateEvent ce) {
        Date dayBefore = eventHelper.getDayBefore(ce.getEffectiveDate());
        SharePrice sharePrice = eventHelper.getSharePrice(timespan.getVariationId(), ce.getIsin(), dayBefore);
        if (sharePrice == null) {
            throw new IllegalStateException("Last closing price could not be calculated on " + dayBefore);
        }
        Optional<IndexVariationSecurity> ivs = securities.stream().filter(f -> f.getIsin().equals(ce.getIsin())).findFirst();
        if (ivs.isPresent()) {
            Distribution distribution = (Distribution)ce;
            Double fraction = ivs.get().getFraction();
            fraction = fraction * (sharePrice.getPrice() / (sharePrice.getPrice() - distribution.getNetDistributionRate()));
            ivs.get().setFraction(fraction);
            return securities;
        } else {
            throw new IllegalStateException("No such security in this timespan");
        }
    }
}
