package com.nttdata.dtl.model.common;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(SecuritySymbolId.class)
public class SecuritySymbol {
    @Id
    private String isin;
    @Id
    private String symbol;
    private String origin;
    private String currency;

    public SecuritySymbol() {
    }

    public SecuritySymbol(String isin, String symbol, String origin, String currency) {
        this.isin = isin;
        this.symbol = symbol;
        this.origin = origin;
        this.currency = currency;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecuritySymbol that = (SecuritySymbol) o;
        return Objects.equals(isin, that.isin) &&
                Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isin, symbol);
    }

    @Override
    public String toString() {
        return "SecuritySymbol{" +
                "isin='" + isin + '\'' +
                ", symbol='" + symbol + '\'' +
                ", origin='" + origin + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
