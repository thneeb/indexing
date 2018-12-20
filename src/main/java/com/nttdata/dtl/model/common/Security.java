package com.nttdata.dtl.model.common;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Security {
    @Id
    @NotNull
    @Column(length = 12, nullable = false, unique = true)
    private String isin;
    @Column(length = 50, nullable = false)
    @NotNull
    private String name;

    public Security() {
    }

    public Security(Security security) {
        this.isin = security.isin;
        this.name = security.name;
    }

    public Security(String isin, String name) {
        this.isin = isin;
        this.name = name;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Security security = (Security) o;
        return Objects.equals(isin, security.isin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isin);
    }

    @Override
    public String toString() {
        return "Security{" +
                "isin='" + isin + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
