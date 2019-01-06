package de.neebs.indexing.model.event;

import java.util.Date;

public interface CorporateEvent {
    Integer getEventId();

    void setEventId(Integer eventId);

    String getIsin();

    void setIsin(String isin);

    Date getEffectiveDate();

    void setEffectiveDate(Date effectiveDate);
}
