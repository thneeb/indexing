package com.nttdata.dtl.model.index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@IdClass(IndexVariationSecurityId.class)
public class IndexVariationSecurity {
    @Id
    @Column(nullable = false)
    private Integer timespanId;
    @Id
    @NotNull
    @Column(length = 12, nullable = false)
    private String isin;
    @NotNull
    private Double weight;

    public IndexVariationSecurity() {
    }

    public Integer getTimespanId() {
        return timespanId;
    }

    public void setTimespanId(Integer timespanId) {
        this.timespanId = timespanId;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexVariationSecurity that = (IndexVariationSecurity) o;
        return Objects.equals(timespanId, that.timespanId) &&
                Objects.equals(isin, that.isin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timespanId, isin);
    }

    @Override
    public String toString() {
        return "IndexVariationSecurity{" +
                "timespanId=" + timespanId +
                ", isin='" + isin + '\'' +
                ", weight=" + weight +
                '}';
    }
}
