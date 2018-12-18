package com.nttdata.dtl.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(indexes = {
    @Index(columnList = "wkn", unique = true),
    @Index(columnList = "symbol")
})
public class Share {
    @Id
    @Column(length = 12, nullable = false, unique = true)
    private String isin;
    @Column(length = 6, nullable = false, unique = true)
    private String wkn;
    @Column(length = 12)
    private String symbol;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 3, nullable = false)
    private String currency;

    public Share() {
    }

    public Share(String isin, String wkn, String symbol, String name, String currency) {
        this.isin = isin;
        this.wkn = wkn;
        this.symbol = symbol;
        this.name = name;
        this.currency = currency;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getWkn() {
        return wkn;
    }

    public void setWkn(String wkn) {
        this.wkn = wkn;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Share share = (Share) o;
        return Objects.equals(isin, share.isin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isin);
    }

    @Override
    public String toString() {
        return "Share{" +
                "isin='" + isin + '\'' +
                ", wkn='" + wkn + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
