package de.neebs.indexing.model.ultumus;

public class UltumusValue {
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "UltumusValue{" +
                "value=" + value +
                '}';
    }
}
