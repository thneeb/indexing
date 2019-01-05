package de.neebs.indexing.model.event;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_distribution")
public class Distribution extends AbstractCorporateEvent {
    private double netDistributionRate;

    public double getNetDistributionRate() {
        return netDistributionRate;
    }

    public void setNetDistributionRate(double netDistributionRate) {
        this.netDistributionRate = netDistributionRate;
    }
}
