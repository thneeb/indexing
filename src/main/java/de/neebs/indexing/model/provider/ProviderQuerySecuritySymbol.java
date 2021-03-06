package de.neebs.indexing.model.provider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(ProviderQuerySecuritySymbolId.class)
public class ProviderQuerySecuritySymbol {
    @Id
    private int providerQueryId;
    @Id
    @Column(length = 12)
    private String isin;
    @Id
    @Column(length = 20)
    private String symbol;
    private Integer intervalInMinutes;
    private Date lastRun;
    private int providerId;

    public ProviderQuerySecuritySymbol() {
    }

    public ProviderQuerySecuritySymbol(int providerQueryId, String isin, String symbol, int intervalInMinutes, Date lastRun) {
        this.providerQueryId = providerQueryId;
        this.isin = isin;
        this.symbol = symbol;
        this.intervalInMinutes = intervalInMinutes;
        this.lastRun = lastRun;
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

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderQuerySecuritySymbol that = (ProviderQuerySecuritySymbol) o;
        return providerQueryId == that.providerQueryId &&
                Objects.equals(isin, that.isin) &&
                Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerQueryId, isin, symbol);
    }

    @Override
    public String toString() {
        return "ProviderQuerySecuritySymbol{" +
                "providerQueryId=" + providerQueryId +
                ", isin='" + isin + '\'' +
                ", symbol='" + symbol + '\'' +
                ", intervalInMinutes=" + intervalInMinutes +
                ", lastRun=" + lastRun +
                ", providerId=" + providerId +
                '}';
    }
}
