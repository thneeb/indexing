package de.neebs.indexing.model.event;

import java.util.Date;

public class SecurityTimespan {
    private String isin;
    private Date validFrom;
    private Date validTo;

    public SecurityTimespan() {
    }

    public SecurityTimespan(String isin, Date validFrom, Date validTo) {
        this.isin = isin;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

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

    @Override
    public String toString() {
        return "SecurityTimespan{" +
                "isin='" + isin + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }
}
