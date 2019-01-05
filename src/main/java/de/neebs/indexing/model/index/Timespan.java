package de.neebs.indexing.model.index;

import java.util.Date;

public interface Timespan {
    Date getValidFrom();
    Date getValidTo();

    void setValidFrom(Date validFrom);
    void setValidTo(Date validTo);

    Object clone();
}
