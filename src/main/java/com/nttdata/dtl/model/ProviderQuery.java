package com.nttdata.dtl.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ProviderQuery {
    @Id
    @GeneratedValue
    private int providerQueryId;
    private int providerId;
    private String name;
    private ShareExchange shareExchange;

    public ProviderQuery() {
    }

    public ProviderQuery(int providerQueryId, int providerId, String name, ShareExchange shareExchange) {
        this.providerQueryId = providerQueryId;
        this.providerId = providerId;
        this.name = name;
        this.shareExchange = shareExchange;
    }

    public int getProviderQueryId() {
        return providerQueryId;
    }

    public void setProviderQueryId(int providerQueryId) {
        this.providerQueryId = providerQueryId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShareExchange getShareExchange() {
        return shareExchange;
    }

    public void setShareExchange(ShareExchange shareExchange) {
        this.shareExchange = shareExchange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderQuery that = (ProviderQuery) o;
        return providerQueryId == that.providerQueryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerQueryId);
    }

    @Override
    public String toString() {
        return "ProviderQuery{" +
                "providerQueryId=" + providerQueryId +
                ", providerId=" + providerId +
                ", name='" + name + '\'' +
                ", shareExchange=" + shareExchange +
                '}';
    }
}
