package de.neebs.indexing.model.ultumus;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UltumusMeta {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date lastUpdated;

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
