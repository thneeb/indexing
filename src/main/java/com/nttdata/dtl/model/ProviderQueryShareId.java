package com.nttdata.dtl.model;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class ProviderQueryShareId implements Serializable {
    private int providerQueryId;
    private String isin;

    public ProviderQueryShareId() {
    }

    public ProviderQueryShareId(int providerQueryId, String isin) {
        this.providerQueryId = providerQueryId;
        this.isin = isin;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderQueryShareId that = (ProviderQueryShareId) o;
        return providerQueryId == that.providerQueryId &&
                Objects.equals(isin, that.isin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerQueryId, isin);
    }

    @Override
    public String toString() {
        return "ProviderQueryShareId{" +
                "providerQueryId=" + providerQueryId +
                ", isin='" + isin + '\'' +
                '}';
    }
}
