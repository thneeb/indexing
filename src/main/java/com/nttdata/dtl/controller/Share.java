package com.nttdata.dtl.controller;

public class Share {
    private String isin;
    private String wkn;
    private String symbol;

    public Share() {
    }

    public Share(String isin, String wkn, String symbol) {
        this.isin = isin;
        this.wkn = wkn;
        this.symbol = symbol;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getWkn() {
        return wkn;
    }

    public void setWkn(String wkn) {
        this.wkn = wkn;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
