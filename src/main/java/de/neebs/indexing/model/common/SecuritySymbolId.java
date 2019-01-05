package de.neebs.indexing.model.common;

import java.io.Serializable;
import java.util.Objects;

public class SecuritySymbolId implements Serializable {
    private String isin;
    private String symbol;

    public SecuritySymbolId() {
    }

    public SecuritySymbolId(String isin, String symbol) {
        this.isin = isin;
        this.symbol = symbol;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecuritySymbolId that = (SecuritySymbolId) o;
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
                '}';
    }
}
