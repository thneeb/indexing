package de.neebs.indexing.model.event;

import java.util.Date;

public interface CorporateEvent {
    int getEventId();

    void setEventId(int eventId);

    String getIsin();

    void setIsin(String isin);

    Date getEffectiveDate();

    void setEffectiveDate(Date effectiveDate);
}
