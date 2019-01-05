package de.neebs.indexing.model.event;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_insolvency")
public class Insolvency extends AbstractCorporateEvent {
}
