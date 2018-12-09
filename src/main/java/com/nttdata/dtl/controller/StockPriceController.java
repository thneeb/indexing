package com.nttdata.dtl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
@RequestMapping("/stockprice")
public class StockPriceController {
    @Autowired
    private Alphavantage alphavantage;

    @Autowired
    private ShareFactory shareFactory;

    @Autowired
    private StockPriceRepository repository;

    @RequestMapping("/test")
    public ResponseEntity<?> test() {
        Share MSFT = shareFactory.listShares().get(0);
        List<StockPrice> prices = alphavantage.retrievePrices(MSFT, Interval.INTRADAY, OutputSize.FULL);
        repository.save(prices);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2018, 11, 3, 12, 9, 0);
        StockPriceKey key = new StockPriceKey("alphavantage", Interval.INTRADAY.toString(), calendar.getTime(), MSFT.getIsin(), PriceType.OPEN.toString());
        StockPrice price = repository.findOne(key);
        System.out.println(key);
        return ResponseEntity.ok(price);
    }

    @RequestMapping("/retrieve")
    public ResponseEntity<?> retrieveStockQuotes() {
        return ResponseEntity.ok("success");
    }
}
