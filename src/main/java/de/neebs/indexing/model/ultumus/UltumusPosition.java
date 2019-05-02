package de.neebs.indexing.model.ultumus;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class UltumusPosition {
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date referenceDate;
    private Object cache;
    private Integer numberOfAssets;
    private UltumusValue indexValue;
    private UltumusValue divisor;
    private List<UltumusAsset> assets;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Object getCache() {
        return cache;
    }

    public void setCache(Object cache) {
        this.cache = cache;
    }

    public Integer getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(Integer numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public UltumusValue getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(UltumusValue indexValue) {
        this.indexValue = indexValue;
    }

    public UltumusValue getDivisor() {
        return divisor;
    }

    public void setDivisor(UltumusValue divisor) {
        this.divisor = divisor;
    }

    public List<UltumusAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<UltumusAsset> assets) {
        this.assets = assets;
    }

    @Override
    public String toString() {
        return "UltumusPosition{" +
                "type='" + type + '\'' +
                ", referenceDate=" + referenceDate +
                ", cache=" + cache +
                ", numberOfAssets=" + numberOfAssets +
                ", indexValue=" + indexValue +
                ", divisor=" + divisor +
                ", assets=" + assets +
                '}';
    }
}
