package de.neebs.indexing.model.index;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class IndexVariationTimespan implements Timespan, Cloneable {
    @Id
    @TableGenerator(name = "SQ_INDEX_TIMESPAN", table = "HIBERNATE_SEQUENCES", initialValue = 1001, pkColumnValue = "index_variation_timespan")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_INDEX_TIMESPAN")
    private Integer timespanId;
    @Column(nullable = false)
    private Integer variationId;
    @Column(nullable = false)
    private Date validFrom;
    @Column(nullable = false)
    private Date validTo;

    public IndexVariationTimespan() {
    }

    public IndexVariationTimespan(Integer variationId, Date validFrom, Date validTo) {
        this.variationId = variationId;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public Integer getTimespanId() {
        return timespanId;
    }

    public void setTimespanId(Integer timespanId) {
        this.timespanId = timespanId;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
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
        IndexVariationTimespan that = (IndexVariationTimespan) o;
        return Objects.equals(timespanId, that.timespanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timespanId);
    }

    @Override
    public String toString() {
        return "IndexVariationTimespan{" +
                "timespanId=" + timespanId +
                ", variationId=" + variationId +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }

    @Override
    public Object clone() {
        try {
            IndexVariationTimespan clone = (IndexVariationTimespan)super.clone();
            clone.setTimespanId(null);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
