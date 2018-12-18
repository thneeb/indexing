package com.nttdata.dtl.model.provider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(ProviderQueryShareId.class)
public class ProviderQueryShare {
    @Id
    private int providerQueryId;
    @Id
    @Column(length = 12)
    private String isin;
    private int intervalInMinutes;
    private Date lastRun;

    public ProviderQueryShare() {
    }

    public ProviderQueryShare(int providerQueryId, String isin, int intervalInMinutes, Date lastRun) {
        this.providerQueryId = providerQueryId;
        this.isin = isin;
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

    public int getIntervalInMinutes() {
        return intervalInMinutes;
    }

    public void setIntervalInMinutes(int intervalInMinutes) {
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
        ProviderQueryShare that = (ProviderQueryShare) o;
        return providerQueryId == that.providerQueryId &&
                Objects.equals(isin, that.isin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerQueryId, isin);
    }

    @Override
    public String toString() {
        return "ProviderQueryShare{" +
                "providerQueryId=" + providerQueryId +
                ", isin='" + isin + '\'' +
                ", intervalInMinutes=" + intervalInMinutes +
                ", lastRun=" + lastRun +
                '}';
    }
}
