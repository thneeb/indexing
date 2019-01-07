package de.neebs.indexing.model.provider;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ExchangeRateId implements Serializable {
    private String currencyFrom;
    private String currencyTo;
    private int providerQueryId;
    private Date timestamp;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateId that = (ExchangeRateId) o;
        return providerQueryId == that.providerQueryId &&
                Objects.equals(currencyFrom, that.currencyFrom) &&
                Objects.equals(currencyTo, that.currencyTo) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyFrom, currencyTo, providerQueryId, timestamp);
    }
}
