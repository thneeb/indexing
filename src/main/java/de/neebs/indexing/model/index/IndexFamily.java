package de.neebs.indexing.model.index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class IndexFamily {
    @Id
    @TableGenerator(name = "SQ_INDEX_FAMILY", table = "HIBERNATE_SEQUENCES", initialValue = 1001, pkColumnValue = "index_family")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_INDEX_FAMILY")
    private int familyId;
    @Column(length = 50, nullable = false)
    @NotNull
    private String name;
    private Integer parentId;

    public IndexFamily() {
    }

    public IndexFamily(String name) {
        this.name = name;
    }

    public IndexFamily(int familyId, String name, Integer parentId) {
        this.familyId = familyId;
        this.name = name;
        this.parentId = parentId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexFamily that = (IndexFamily) o;
        return familyId == that.familyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(familyId);
    }

    @Override
    public String toString() {
        return "IndexFamily{" +
                "familyId=" + familyId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
