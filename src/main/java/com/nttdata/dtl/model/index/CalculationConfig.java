package com.nttdata.dtl.model.index;

import com.nttdata.dtl.model.provider.OpenHighCloseLow;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CalculationConfig {
    @NotNull
    private String name;
    @NotNull
    private OpenHighCloseLow openHighCloseLow;
    private Date calculateFrom;
    private Date calculateTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpenHighCloseLow getOpenHighCloseLow() {
        return openHighCloseLow;
    }

    public void setOpenHighCloseLow(OpenHighCloseLow openHighCloseLow) {
        this.openHighCloseLow = openHighCloseLow;
    }

    public Date getCalculateFrom() {
        return calculateFrom;
    }

    public void setCalculateFrom(Date calculateFrom) {
        this.calculateFrom = calculateFrom;
    }

    public Date getCalculateTo() {
        return calculateTo;
    }

    public void setCalculateTo(Date calculateTo) {
        this.calculateTo = calculateTo;
    }
}
