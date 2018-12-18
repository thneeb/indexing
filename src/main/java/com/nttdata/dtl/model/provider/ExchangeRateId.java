package com.nttdata.dtl.model.provider;

import java.io.Serializable;
import java.util.Date;

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
}
