package de.neebs.indexing.model.event;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_capital_increase")
public class CapitalIncrease extends AbstractCorporateEvent {
    private double priceOfRightsIssue;
    private double distributionDisadvantage;
    private double subscriptionRatio;

    public double getPriceOfRightsIssue() {
        return priceOfRightsIssue;
    }

    public void setPriceOfRightsIssue(double priceOfRightsIssue) {
        this.priceOfRightsIssue = priceOfRightsIssue;
    }

    public double getDistributionDisadvantage() {
        return distributionDisadvantage;
    }

    public void setDistributionDisadvantage(double distributionDisadvantage) {
        this.distributionDisadvantage = distributionDisadvantage;
    }

    public double getSubscriptionRatio() {
        return subscriptionRatio;
    }

    public void setSubscriptionRatio(double subscriptionRatio) {
        this.subscriptionRatio = subscriptionRatio;
    }
}
