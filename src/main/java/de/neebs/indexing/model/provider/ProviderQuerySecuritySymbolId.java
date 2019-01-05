package de.neebs.indexing.model.provider;

import java.io.Serializable;
import java.util.Objects;

public class ProviderQuerySecuritySymbolId implements Serializable {
    private int providerQueryId;
    private String isin;
    private String symbol;

    public ProviderQuerySecuritySymbolId() {
    }

    public ProviderQuerySecuritySymbolId(int providerQueryId, String isin, String symbol) {
        this.providerQueryId = providerQueryId;
        this.isin = isin;
        this.symbol = symbol;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderQuerySecuritySymbolId that = (ProviderQuerySecuritySymbolId) o;
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
        return "ProviderQuerySecuritySymbolId{" +
                "providerQueryId=" + providerQueryId +
                ", isin='" + isin + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
