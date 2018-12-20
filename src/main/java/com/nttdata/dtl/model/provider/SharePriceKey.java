package com.nttdata.dtl.model.provider;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SharePriceKey implements Serializable {
    private int providerQueryId;
    private String isin;
    private String symbol;
    private Date timestamp;
    private OpenHighCloseLow openHighCloseLow;

    public SharePriceKey() {
    }

    public SharePriceKey(int providerQueryId, String isin, String symbol, Date timestamp, OpenHighCloseLow openHighCloseLow) {
        this.providerQueryId = providerQueryId;
        this.isin = isin;
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.openHighCloseLow = openHighCloseLow;
    }

    public int getProviderQueryId() {
        return providerQueryId;
    }

    public void setProviderQueryId(int providerQueryId) {
        this.providerQueryId = providerQueryId;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SharePriceKey that = (SharePriceKey) o;
        return providerQueryId == that.providerQueryId &&
                Objects.equals(isin, that.isin) &&
                Objects.equals(symbol, that.symbol) &&
                Objects.equals(timestamp, that.timestamp) &&
                openHighCloseLow == that.openHighCloseLow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerQueryId, isin, symbol, timestamp, openHighCloseLow);
    }

    @Override
    public String toString() {
        return "SharePriceKey{" +
                "providerQueryId=" + providerQueryId +
                ", isin='" + isin + '\'' +
                ", symbol='" + symbol + '\'' +
                ", timestamp=" + timestamp +
                ", openHighCloseLow=" + openHighCloseLow +
                '}';
    }
}
