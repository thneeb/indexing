package com.nttdata.dtl.controller;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

@Entity @IdClass(StockPriceKey.class)
public class StockPrice {
    @Id
    private String provider;
    @Id
    private String intervalType;
    @Id
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private Date timestamp;
    @Id
    private String isin;
    @Id
    private String type;
    private long price;
    private String currency;
    private long volume;

    public StockPrice() {
    }

    public StockPrice(String provider, String intervalType, Date timestamp, String isin, String type, long price, String currency, long volume) {
        this.provider = provider;
        this.intervalType = intervalType;
        this.timestamp = timestamp;
        this.isin = isin;
        this.type = type;
        this.price = price;
        this.currency = currency;
        this.volume = volume;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
