package de.neebs.indexing.model.index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class IndexVariation {
    @Id
    @GeneratedValue
    private Integer variationId;
    @NotNull
    @Column(length = 100, nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private Integer masterdataId;

    public IndexVariation() {
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMasterdataId() {
        return masterdataId;
    }

    public void setMasterdataId(Integer masterdataId) {
        this.masterdataId = masterdataId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexVariation that = (IndexVariation) o;
        return Objects.equals(variationId, that.variationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variationId);
    }

    @Override
    public String toString() {
        return "IndexVariation{" +
                "variationId=" + variationId +
                ", name='" + name + '\'' +
                ", masterdataId=" + masterdataId +
                '}';
    }
}
