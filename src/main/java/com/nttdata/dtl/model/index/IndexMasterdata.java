package com.nttdata.dtl.model.index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class IndexMasterdata {
    @Id
    @GeneratedValue
    private Integer masterdataId;
    @NotNull
    @Column(length = 100, nullable = false, unique = true)
    private String name;
    @NotNull
    @Column(length = 3, nullable = false)
    private String currency;
    private Integer familyId;

    public IndexMasterdata() {
    }

    public IndexMasterdata(Integer masterdataId, String name, String currency, Integer familyId) {
        this.masterdataId = masterdataId;
        this.name = name;
        this.currency = currency;
        this.familyId = familyId;
    }

    public Integer getMasterdataId() {
        return masterdataId;
    }

    public void setMasterdataId(Integer masterdataId) {
        this.masterdataId = masterdataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexMasterdata that = (IndexMasterdata) o;
        return Objects.equals(masterdataId, that.masterdataId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(masterdataId);
    }

    @Override
    public String toString() {
        return "IndexMasterdata{" +
                "masterdataId=" + masterdataId +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", familyId=" + familyId +
                '}';
    }
}
