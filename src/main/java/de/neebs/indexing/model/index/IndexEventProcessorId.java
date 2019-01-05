package de.neebs.indexing.model.index;

import java.io.Serializable;
import java.util.Objects;

public class IndexEventProcessorId implements Serializable {
    private int masterdataId;
    private String event;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexEventProcessorId that = (IndexEventProcessorId) o;
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
                '}';
    }
}
