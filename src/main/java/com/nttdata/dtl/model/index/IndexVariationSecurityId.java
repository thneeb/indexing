package com.nttdata.dtl.model.index;

import java.io.Serializable;
import java.util.Objects;

public class IndexVariationSecurityId implements Serializable {
    private Integer timespanId;
    private String isin;

    public IndexVariationSecurityId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexVariationSecurityId that = (IndexVariationSecurityId) o;
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
                '}';
    }
}
