package de.neebs.indexing.model.provider;

import java.io.Serializable;
import java.util.Objects;

public class ProviderSymbolId implements Serializable {
    private int providerId;
    private String isin;
    private String symbol;

    public ProviderSymbolId() {
    }

    public ProviderSymbolId(int providerId, String isin, String symbol) {
        this.providerId = providerId;
        this.isin = isin;
        this.symbol = symbol;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
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
        ProviderSymbolId that = (ProviderSymbolId) o;
        return providerId == that.providerId &&
                Objects.equals(isin, that.isin) &&
                Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, isin, symbol);
    }

    @Override
    public String toString() {
        return "ProviderSymbolId{" +
                "providerId=" + providerId +
                ", isin='" + isin + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
