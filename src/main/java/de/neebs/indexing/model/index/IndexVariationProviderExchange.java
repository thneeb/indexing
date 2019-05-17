package de.neebs.indexing.model.index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class IndexVariationProviderExchange implements Timespan, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer providerExchangeId;
    @Column(nullable = false)
    private Integer providerQueryId;
    @Column(nullable = false)
    private Integer variationId;
    @NotNull
    @Column(length = 3, nullable = false)
    private String currencyFrom;
    @Column(length = 3, nullable = false)
    private String currencyTo;
    @Column(nullable = false)
    private Date validFrom;
    @Column(nullable = false)
    private Date validTo;
    @NotNull
    @Column(nullable = false)
    private double quality;

    public IndexVariationProviderExchange() {
    }

    public Integer getProviderExchangeId() {
        return providerExchangeId;
    }

    public void setProviderExchangeId(Integer providerExchangeId) {
        this.providerExchangeId = providerExchangeId;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public Integer getProviderQueryId() {
        return providerQueryId;
    }

    public void setProviderQueryId(Integer providerQueryId) {
        this.providerQueryId = providerQueryId;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
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

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexVariationProviderExchange that = (IndexVariationProviderExchange) o;
        return Objects.equals(providerExchangeId, that.providerExchangeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerExchangeId);
    }

    @Override
    public String toString() {
        return "IndexVariationProviderSecurity{" +
                "providerExchangeId=" + providerExchangeId +
                ", providerQueryId=" + providerQueryId +
                ", variationId=" + variationId +
                ", currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", quality=" + quality +
                '}';
    }

    @Override
    public Object clone() {
        try {
            IndexVariationProviderExchange obj = (IndexVariationProviderExchange)super.clone();
            obj.setProviderExchangeId(null);
            return obj;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
