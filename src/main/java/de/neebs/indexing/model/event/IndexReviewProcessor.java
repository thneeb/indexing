package de.neebs.indexing.model.event;

import de.neebs.indexing.model.index.IndexVariationSecurity;
import de.neebs.indexing.model.index.IndexVariationTimespan;

import java.util.Date;
import java.util.List;

public interface IndexReviewProcessor {
    Date getNextReviewDate(Date date);

    List<IndexVariationSecurity> initialPeriod(IndexVariationTimespan timespan);

    List<IndexVariationSecurity> nextPeriod(IndexVariationTimespan nextTimespan, List<IndexVariationSecurity> lastSecurities);
}
