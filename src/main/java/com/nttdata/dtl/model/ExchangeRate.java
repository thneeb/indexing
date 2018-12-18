package com.nttdata.dtl.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(ExchangeRateId.class)
public class ExchangeRate {
    @Id
    private String currencyFrom;
    @Id
    private String currencyTo;
    @Id
    private int providerQueryId;
    @Id
    private Date timestamp;
    @Id
    private OpenHighCloseLow openHighCloseLow;
    private double rate;

    public ExchangeRate() {
    }

    public ExchangeRate(String currencyFrom, String currencyTo, int providerQueryId, Date timestamp, OpenHighCloseLow openHighCloseLow, double rate) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.providerQueryId = providerQueryId;
        this.timestamp = timestamp;
        this.openHighCloseLow = openHighCloseLow;
        this.rate = rate;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public int getProviderQueryId() {
        return providerQueryId;
    }

    public void setProviderQueryId(int providerQueryId) {
        this.providerQueryId = providerQueryId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public OpenHighCloseLow getOpenHighCloseLow() {
        return openHighCloseLow;
    }

    public void setOpenHighCloseLow(OpenHighCloseLow openHighCloseLow) {
        this.openHighCloseLow = openHighCloseLow;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return providerQueryId == that.providerQueryId &&
                Objects.equals(currencyFrom, that.currencyFrom) &&
                Objects.equals(currencyTo, that.currencyTo) &&
                Objects.equals(timestamp, that.timestamp) &&
                openHighCloseLow == that.openHighCloseLow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyFrom, currencyTo, providerQueryId, timestamp, openHighCloseLow);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", providerQueryId=" + providerQueryId +
                ", timestamp=" + timestamp +
                ", openHighCloseLow=" + openHighCloseLow +
                ", rate=" + rate +
                '}';
    }
}
