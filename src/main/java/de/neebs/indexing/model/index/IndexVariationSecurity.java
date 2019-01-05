package de.neebs.indexing.model.index;

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
    @Column(nullable = false)
    private Double fraction;

    public IndexVariationSecurity() {
    }

    public IndexVariationSecurity(Integer timespanId, @NotNull String isin, @NotNull Double fraction) {
        this.timespanId = timespanId;
        this.isin = isin;
        this.fraction = fraction;
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

    public Double getFraction() {
        return fraction;
    }

    public void setFraction(Double fraction) {
        this.fraction = fraction;
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
                ", fraction=" + fraction +
                '}';
    }
}
