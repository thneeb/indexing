package de.neebs.indexing.model.ultumus;

import java.util.List;

public class UltumusSummary {
    private UltumusMeta meta;
    private List<UltumusSummaryEntry> data;

    public UltumusMeta getMeta() {
        return meta;
    }

    public void setMeta(UltumusMeta meta) {
        this.meta = meta;
    }

    public List<UltumusSummaryEntry> getData() {
        return data;
    }

    public void setData(List<UltumusSummaryEntry> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UltumusSummary{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}
