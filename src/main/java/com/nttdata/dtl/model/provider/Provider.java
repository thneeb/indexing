package com.nttdata.dtl.model.provider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Provider {
    @Id
    @GeneratedValue
    private int providerId;
    @Column(length = 50)
    private String name;
    private int callsPerMinute;

    public Provider() {
    }

    public Provider(int providerId, String name, int callsPerMinute) {
        this.providerId = providerId;
        this.name = name;
        this.callsPerMinute = callsPerMinute;
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

    public int getCallsPerMinute() {
        return callsPerMinute;
    }

    public void setCallsPerMinute(int callsPerMinute) {
        this.callsPerMinute = callsPerMinute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return providerId == provider.providerId &&
                Objects.equals(name, provider.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, name);
    }

    @Override
    public String toString() {
        return "Provider{" +
                "providerId=" + providerId +
                ", name='" + name + '\'' +
                '}';
    }
}
