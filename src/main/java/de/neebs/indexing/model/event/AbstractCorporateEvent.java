package de.neebs.indexing.model.event;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "event_base")
public abstract class AbstractCorporateEvent implements CorporateEvent {
    @Id
    @TableGenerator(name = "SQ_CORPORATE_EVENT", table = "HIBERNATE_SEQUENCES", initialValue = 1001, pkColumnValue = "event_base")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_CORPORATE_EVENT")
    private Integer eventId;
    @NotNull
    @Column(nullable = false, length = 12)
    private String isin;
    @NotNull
    @Column(nullable = false)
    private Date effectiveDate;

    public AbstractCorporateEvent() {
    }

    public AbstractCorporateEvent(@NotNull String isin, @NotNull Date effectiveDate) {
        this.isin = isin;
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Integer getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getIsin() {
        return isin;
    }

    @Override
    public void setIsin(String isin) {
        this.isin = isin;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
