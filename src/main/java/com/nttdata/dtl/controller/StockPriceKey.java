package com.nttdata.dtl.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class StockPriceKey implements Serializable {
    private String provider;
    private String intervalType;
    private Date timestamp;
    private String isin;
    private String type;

    public StockPriceKey() {
    }

    public StockPriceKey(String provider, String intervalType, Date timestamp, String isin, String type) {
        this.provider = provider;
        this.intervalType = intervalType;
        this.timestamp = timestamp;
        this.isin = isin;
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(String intervalType) {
        this.intervalType = intervalType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockPriceKey that = (StockPriceKey) o;
        return Objects.equals(provider, that.provider) &&
                Objects.equals(intervalType, that.intervalType) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(isin, that.isin) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(provider, intervalType, timestamp, isin, type);
    }

    @Override
    public String toString() {
        return "StockPriceKey{" +
                "provider='" + provider + '\'' +
                ", intervalType='" + intervalType + '\'' +
                ", timestamp=" + timestamp +
                ", isin='" + isin + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
