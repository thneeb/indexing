package de.neebs.indexing.model.ultumus;

public class UltumusCompositionData {
    private UltumusSummaryEntry index;
    private String provider;
    private String variantType;
    private String currency;
    private UltumusPositions positions;

    public UltumusSummaryEntry getIndex() {
        return index;
    }

    public void setIndex(UltumusSummaryEntry index) {
        this.index = index;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getVariantType() {
        return variantType;
    }

    public void setVariantType(String variantType) {
        this.variantType = variantType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UltumusPositions getPositions() {
        return positions;
    }

    public void setPositions(UltumusPositions positions) {
        this.positions = positions;
    }
}
