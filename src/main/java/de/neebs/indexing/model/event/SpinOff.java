package de.neebs.indexing.model.event;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_spinoff")
public class SpinOff extends AbstractCorporateEvent {
    private String spinOffIsin;
    private String spinOffSymbol;
    private double spinOffFraction;
    private int spinOffProviderQueryId;
    private double spinOffProviderQuality;

    public String getSpinOffIsin() {
        return spinOffIsin;
    }

    public void setSpinOffIsin(String spinOffIsin) {
        this.spinOffIsin = spinOffIsin;
    }

    public String getSpinOffSymbol() {
        return spinOffSymbol;
    }

    public void setSpinOffSymbol(String spinOffSymbol) {
        this.spinOffSymbol = spinOffSymbol;
    }

    public double getSpinOffFraction() {
        return spinOffFraction;
    }

    public void setSpinOffFraction(double spinOffFraction) {
        this.spinOffFraction = spinOffFraction;
    }

    public int getSpinOffProviderQueryId() {
        return spinOffProviderQueryId;
    }

    public void setSpinOffProviderQueryId(int spinOffProviderQueryId) {
        this.spinOffProviderQueryId = spinOffProviderQueryId;
    }

    public double getSpinOffProviderQuality() {
        return spinOffProviderQuality;
    }

    public void setSpinOffProviderQuality(double spinOffProviderQuality) {
        this.spinOffProviderQuality = spinOffProviderQuality;
    }
}
