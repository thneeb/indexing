package com.nttdata.dtl.model.common;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class TimeSeries {
    @Id
    private Date theDate;

    public Date getTheDate() {
        return theDate;
    }

    public void setTheDate(Date theDate) {
        this.theDate = theDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSeries that = (TimeSeries) o;
        return Objects.equals(theDate, that.theDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theDate);
    }

    @Override
    public String toString() {
        return "TimeSeries{" +
                "theDate=" + theDate +
                '}';
    }
}
