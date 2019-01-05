package de.neebs.indexing.model.provider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(ProviderQueryExchangeRateId.class)
public class ProviderQueryExchangeRate {
    @Id
    private int providerQueryId;
    @Id
    @Column(length = 3, nullable = false)
    private String currencyFrom;
    @Id
    @Column(length = 3, nullable = false)
    private String currencyTo;
    private Integer intervalInMinutes;
    private Date lastRun;

    public int getProviderQueryId() {
        return providerQueryId;
    }

    public void setProviderQueryId(int providerQueryId) {
        this.providerQueryId = providerQueryId;
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

    public Integer getIntervalInMinutes() {
        return intervalInMinutes;
    }

    public void setIntervalInMinutes(Integer intervalInMinutes) {
        this.intervalInMinutes = intervalInMinutes;
    }

    public Date getLastRun() {
        return lastRun;
    }

    public void setLastRun(Date lastRun) {
        this.lastRun = lastRun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderQueryExchangeRate that = (ProviderQueryExchangeRate) o;
        return providerQueryId == that.providerQueryId &&
                Objects.equals(currencyFrom, that.currencyFrom) &&
                Objects.equals(currencyTo, that.currencyTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerQueryId, currencyFrom, currencyTo);
    }

    @Override
    public String toString() {
        return "ProviderQueryExchangeRate{" +
                "providerQueryId=" + providerQueryId +
                ", currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", intervalInMinutes=" + intervalInMinutes +
                ", lastRun=" + lastRun +
                '}';
    }
}
