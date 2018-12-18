package com.nttdata.dtl.controller;

import java.util.List;

public class IndexBalance {
    private String name;
    private List<IndexBalanceEntry> entries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IndexBalanceEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<IndexBalanceEntry> entries) {
        this.entries = entries;
    }
}
