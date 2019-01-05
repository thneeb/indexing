package de.neebs.indexing.model.provider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(SharePriceKey.class)
public class SharePrice {
    @Id
    private int providerQueryId;
    @Id
    @Column(length = 12)
    private String isin;
    @Id
    @Column(length = 20)
    private String symbol;
    @Id
    private Date timestamp;
    @Id
    private OpenHighCloseLow openHighCloseLow;
    private double price;
    private long volume;

    public SharePrice() {
    }

    public SharePrice(int providerQueryId, String isin, String symbol, Date timestamp, OpenHighCloseLow openHighCloseLow, double price, long volume) {
        this.providerQueryId = providerQueryId;
        this.isin = isin;
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.openHighCloseLow = openHighCloseLow;
        this.price = price;
        this.volume = volume;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SharePrice that = (SharePrice) o;
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
        return "SharePrice{" +
                "providerQueryId=" + providerQueryId +
                ", isin='" + isin + '\'' +
                ", symbol='" + symbol + '\'' +
                ", timestamp=" + timestamp +
                ", openHighCloseLow=" + openHighCloseLow +
                ", price=" + price +
                ", volume=" + volume +
                '}';
    }
}
