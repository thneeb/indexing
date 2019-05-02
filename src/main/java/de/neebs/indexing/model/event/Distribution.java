package de.neebs.indexing.model.event;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "event_distribution")
public class Distribution extends AbstractCorporateEvent {
    public Distribution() {
    }

    public Distribution(@NotNull String isin, @NotNull Date effectiveDate, @NotNull double netDistributionRate) {
        super(isin, effectiveDate);
        this.netDistributionRate = netDistributionRate;
    }

    @NotNull
    private double netDistributionRate;

    public double getNetDistributionRate() {
        return netDistributionRate;
    }

    public void setNetDistributionRate(double netDistributionRate) {
        this.netDistributionRate = netDistributionRate;
    }
}
