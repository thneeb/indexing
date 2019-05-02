package de.neebs.indexing.model.ultumus;

public class UltumusSecurity {
    private UltumusIdentifiers identifiers;

    public UltumusIdentifiers getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(UltumusIdentifiers identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public String toString() {
        return "UltumusSecurity{" +
                "identifiers=" + identifiers +
                '}';
    }
}
