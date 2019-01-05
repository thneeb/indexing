package de.neebs.indexing.model.index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class IndexVariationProviderSecurity implements Timespan, Cloneable {
    @Id
    @GeneratedValue
    private Integer providerSecurityId;
    @Column(nullable = false)
    private Integer providerQueryId;
    @Column(nullable = false)
    private Integer variationId;
    @NotNull
    @Column(length = 12, nullable = false)
    private String isin;
    @Column(length = 20, nullable = false)
    private String symbol;
    @Column(nullable = false)
    private Date validFrom;
    @Column(nullable = false)
    private Date validTo;
    @NotNull
    @Column(nullable = false)
    private double quality;

    public IndexVariationProviderSecurity() {
    }

    public Integer getProviderSecurityId() {
        return providerSecurityId;
    }

    public void setProviderSecurityId(Integer providerSecurityId) {
        this.providerSecurityId = providerSecurityId;
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
        IndexVariationProviderSecurity that = (IndexVariationProviderSecurity) o;
        return Objects.equals(providerSecurityId, that.providerSecurityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerSecurityId);
    }

    @Override
    public String toString() {
        return "IndexVariationProviderSecurity{" +
                "providerSecurityId=" + providerSecurityId +
                ", providerQueryId=" + providerQueryId +
                ", variationId=" + variationId +
                ", isin='" + isin + '\'' +
                ", symbol='" + symbol + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", quality=" + quality +
                '}';
    }

    @Override
    public Object clone() {
        try {
            IndexVariationProviderSecurity obj = (IndexVariationProviderSecurity)super.clone();
            obj.setProviderSecurityId(null);
            return obj;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
