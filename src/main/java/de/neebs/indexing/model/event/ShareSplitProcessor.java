package de.neebs.indexing.model.event;

import de.neebs.indexing.model.index.IndexVariationSecurity;
import de.neebs.indexing.model.index.IndexVariationTimespan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShareSplitProcessor implements EventProcessor {
    @Override
    public List<IndexVariationSecurity> execute(IndexVariationTimespan timespan, List<IndexVariationSecurity> securities, CorporateEvent ce) {
        Optional<IndexVariationSecurity> ivs = securities.stream().filter(f -> f.getIsin().equals(ce.getIsin())).findFirst();
        if (ivs.isPresent()) {
            ShareSplit shareSplit = (ShareSplit)ce;
            Double fraction = ivs.get().getFraction();
            fraction = fraction * shareSplit.getSplitFactor();
            ivs.get().setFraction(fraction);
            return securities;
        } else {
            throw new IllegalStateException("No such security in this timespan");
        }
    }
}
