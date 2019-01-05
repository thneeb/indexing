package de.neebs.indexing.model.index;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(IndexEventProcessorId.class)
public class IndexEventProcessor {
    @Id
    private int masterdataId;
    @Id
    private String event;
    private String processor;

    public int getMasterdataId() {
        return masterdataId;
    }

    public void setMasterdataId(int masterdataId) {
        this.masterdataId = masterdataId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexEventProcessor that = (IndexEventProcessor) o;
        return masterdataId == that.masterdataId &&
                Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(masterdataId, event);
    }

    @Override
    public String toString() {
        return "IndexReviewProcessor{" +
                "masterdataId=" + masterdataId +
                ", event='" + event + '\'' +
                ", processor='" + processor + '\'' +
                '}';
    }
}
