package de.neebs.indexing.model.ultumus;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UltumusIdentifiers {
    @JsonProperty("UltIV")
    private List<String> ultIV;
    @JsonProperty("ProviderCode")
    private List<String> providerCode;
    @JsonProperty("RIC")
    private List<String> ric;
    @JsonProperty("ISIN")
    private List<String> isin;
    @JsonProperty("Ticket")
    private List<String> ticker;

    public List<String> getUltIV() {
        return ultIV;
    }

    public void setUltIV(List<String> ultIV) {
        this.ultIV = ultIV;
    }

    public List<String> getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(List<String> providerCode) {
        this.providerCode = providerCode;
    }

    public List<String> getRic() {
        return ric;
    }

    public void setRic(List<String> ric) {
        this.ric = ric;
    }

    public List<String> getIsin() {
        return isin;
    }

    public void setIsin(List<String> isin) {
        this.isin = isin;
    }

    public List<String> getTicker() {
        return ticker;
    }

    public void setTicker(List<String> ticker) {
        this.ticker = ticker;
    }

    @Override
    public String toString() {
        return "UltumusIdentifiers{" +
                "ultIV=" + ultIV +
                ", providerCode=" + providerCode +
                ", ric=" + ric +
                ", isin=" + isin +
                ", ticker=" + ticker +
                '}';
    }
}
