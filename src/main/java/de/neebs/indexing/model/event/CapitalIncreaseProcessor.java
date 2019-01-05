package de.neebs.indexing.model.event;

import de.neebs.indexing.model.index.IndexVariationSecurity;
import de.neebs.indexing.model.index.IndexVariationTimespan;
import de.neebs.indexing.model.provider.SharePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class CapitalIncreaseProcessor implements EventProcessor {
    @Autowired
    private EventHelper eventHelper;

    @Override
    public List<IndexVariationSecurity> execute(IndexVariationTimespan timespan, List<IndexVariationSecurity> securities, CorporateEvent ce) {
        Date dayBefore = eventHelper.getDayBefore(ce.getEffectiveDate());
        SharePrice sharePrice = eventHelper.getSharePrice(timespan.getVariationId(), ce.getIsin(), dayBefore);
        if (sharePrice == null) {
            throw new IllegalStateException("Last closing price could not be calculated");
        }
        Optional<IndexVariationSecurity> ivs = securities.stream().filter(f -> f.getIsin().equals(ce.getIsin())).findFirst();
        if (ivs.isPresent()) {
            CapitalIncrease capitalIncrease = (CapitalIncrease)ce;
            double rb = (sharePrice.getPrice() - capitalIncrease.getPriceOfRightsIssue() - capitalIncrease.getDistributionDisadvantage()) /
                    (capitalIncrease.getSubscriptionRatio() + 1);
            double fraction = ivs.get().getFraction();
            fraction = fraction * (sharePrice.getPrice() / (sharePrice.getPrice() - rb));
            ivs.get().setFraction(fraction);
            return securities;
        } else {
            throw new IllegalStateException("No such security in this timespan");
        }
    }
}
