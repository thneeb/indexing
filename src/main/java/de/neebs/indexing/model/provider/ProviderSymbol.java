package de.neebs.indexing.model.provider;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(ProviderSymbolId.class)
public class ProviderSymbol {
    @Id
    private int providerId;
    @Id
    private String isin;
    @Id
    private String symbol;
    private String currency;

    public ProviderSymbol() {
    }

    public ProviderSymbol(int providerId, String isin, String symbol) {
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
        ProviderSymbol that = (ProviderSymbol) o;
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
        return "ProviderSymbol{" +
                "providerId=" + providerId +
                ", isin='" + isin + '\'' +
                ", symbol='" + symbol + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
