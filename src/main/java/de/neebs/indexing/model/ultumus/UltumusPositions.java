package de.neebs.indexing.model.ultumus;

public class UltumusPositions {
    private UltumusPosition open;
    private UltumusPosition close;

    public UltumusPosition getOpen() {
        return open;
    }

    public void setOpen(UltumusPosition open) {
        this.open = open;
    }

    public UltumusPosition getClose() {
        return close;
    }

    public void setClose(UltumusPosition close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "UltumusPositions{" +
                "open=" + open +
                ", close=" + close +
                '}';
    }
}
