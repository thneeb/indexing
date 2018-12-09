package com.nttdata.dtl.controller;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ShareFactory {
    private Share MSFT = new Share("US5949181045", "870747", "MSFT");
    private Share ZBRA = new Share("US9892071054", "882578", "ZBRA");
    private Share XLNX = new Share("US9839191015", "880135", "XLNX");
    private Share VAR = new Share("US92220P1057", "852812", "VAR");
    private Share TRMB = new Share("US8962391004", "882295", "TRMB");

    public List<Share> listShares() {
        return Arrays.asList(MSFT, ZBRA, XLNX, VAR, TRMB);
    }
}
