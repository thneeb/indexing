package de.neebs.indexing.model.ultumus;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UltumusSummaryEntry {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date lastUpdated;
    private String description;
    private UltumusIdentifiers identifiers;
    private String href;

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UltumusIdentifiers getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(UltumusIdentifiers identifiers) {
        this.identifiers = identifiers;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "UltumusSummaryEntry{" +
                "lastUpdated=" + lastUpdated +
                ", description='" + description + '\'' +
                ", identifiers=" + identifiers +
                ", href='" + href + '\'' +
                '}';
    }
}
