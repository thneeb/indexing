package de.neebs.indexing.model.ultumus;

public class UltumusComposition {
    private UltumusMeta meta;
    private UltumusCompositionData data;

    public UltumusMeta getMeta() {
        return meta;
    }

    public void setMeta(UltumusMeta meta) {
        this.meta = meta;
    }

    public UltumusCompositionData getData() {
        return data;
    }

    public void setData(UltumusCompositionData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UltumusComposition{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}
