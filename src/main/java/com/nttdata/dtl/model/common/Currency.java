package com.nttdata.dtl.model.common;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Currency {
    @Id
    private String iso4217;

    public Currency() {
    }

    public Currency(String iso4217) {
        this.iso4217 = iso4217;
    }

    public String getIso4217() {
        return iso4217;
    }

    public void setIso4217(String iso4217) {
        this.iso4217 = iso4217;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(iso4217, currency.iso4217);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iso4217);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "iso4217='" + iso4217 + '\'' +
                '}';
    }
}
