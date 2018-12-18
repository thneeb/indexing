package com.nttdata.dtl.controller;

import com.nttdata.dtl.model.common.Share;

import java.util.Date;
import java.util.Map;

public class IndexBalanceEntry {
    private Date validFrom;
    private Date validTo;
    private Map<Share, Long> distribution;

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Map<Share, Long> getDistribution() {
        return distribution;
    }

    public void setDistribution(Map<Share, Long> distribution) {
        this.distribution = distribution;
    }
}
