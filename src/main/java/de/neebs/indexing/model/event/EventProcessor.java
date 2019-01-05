package de.neebs.indexing.model.event;

import de.neebs.indexing.model.index.IndexVariationSecurity;
import de.neebs.indexing.model.index.IndexVariationTimespan;

import java.util.List;

public interface EventProcessor {
    List<IndexVariationSecurity> execute(IndexVariationTimespan timespan, List<IndexVariationSecurity> securities, CorporateEvent ce);
}
