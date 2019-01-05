package de.neebs.indexing.model.event;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_share_split")
public class ShareSplit extends AbstractCorporateEvent {
    /**
     * The factor the number of shares increases. If after the split the double amount of share is available, the
     * <code>splitFactor</code> must be 2.
     */
    private double splitFactor;

    public double getSplitFactor() {
        return splitFactor;
    }

    public void setSplitFactor(double splitFactor) {
        this.splitFactor = splitFactor;
    }
}
