package de.neebs.indexing.model.index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class IndexVariationProviderSecurity implements Timespan, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer symbolId;
    @Column(nullable = false)
    private Integer providerQueryId;
    @Column(nullable = false)
    private Integer variationId;
    @NotNull
    @Column(length = 12, nullable = false)
    private String isin;
    @Column(length = 50, nullable = false)
    private String symbol;
    @Column(nullable = false)
    private Date validFrom;
    @Column(nullable = false)
    private Date validTo;

    public IndexVariationProviderSecurity() {
    }

    public Integer getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(Integer symbolId) {
        this.symbolId = symbolId;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Integer getProviderQueryId() {
        return providerQueryId;
    }

    public void setProviderQueryId(Integer providerQueryId) {
        this.providerQueryId = providerQueryId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexVariationProviderSecurity that = (IndexVariationProviderSecurity) o;
        return Objects.equals(symbolId, that.symbolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbolId);
    }

    @Override
    public String toString() {
        return "IndexVariationProviderSecurity{" +
                "symbolId=" + symbolId +
                ", providerQueryId=" + providerQueryId +
                ", variationId=" + variationId +
                ", isin='" + isin + '\'' +
                ", symbol='" + symbol + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }

    @Override
    public Object clone() {
        try {
            IndexVariationProviderSecurity obj = (IndexVariationProviderSecurity)super.clone();
            obj.setSymbolId(null);
            return obj;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
