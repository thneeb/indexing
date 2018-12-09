package com.nttdata.dtl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class Alphavantage {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${alphavantage.apikey}")
    private String apikey;

    public List<StockPrice> retrievePrices(Share share, Interval interval, OutputSize outputSize) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("https://www.alphavantage.co/query?");
            if (interval == Interval.INTRADAY) {
                sb.append("function=TIME_SERIES_INTRADAY&interval=1min");
            }
            sb.append("&symbol=");
            sb.append(share.getSymbol());
            if (outputSize == OutputSize.FULL) {
                sb.append("&outputsize=full");
            } else {
                sb.append("&outputsize=compact");
            }
            sb.append("&apikey=");
            sb.append(apikey);
            ResponseEntity<Map> response = restTemplate.getForEntity(new String(sb), Map.class);
            System.out.println(response.getBody());
            List<StockPrice> list = new ArrayList<>();
            if (interval == Interval.INTRADAY) {
                Map<String, Map<String, String>> map = (Map<String, Map<String, String>>)response.getBody().get("Time Series (1min)");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
                    Date date = df.parse(entry.getKey());
                    Map<String, String> price = entry.getValue();
                    long openPrice = (long)(Double.parseDouble(price.get("1. open")) * 10000);
                    long highPrice = (long)(Double.parseDouble(price.get("2. high")) * 10000);
                    long lowPrice = (long)(Double.parseDouble(price.get("3. low")) * 10000);
                    long closePrice = (long)(Double.parseDouble(price.get("4. close")) * 10000);
                    long volume = Long.parseLong(price.get("5. volume"));
                    list.add(new StockPrice("alphavantage", interval.toString(), date, share.getIsin(), PriceType.OPEN.toString(), openPrice, "USD", volume));
                    list.add(new StockPrice("alphavantage", interval.toString(), date, share.getIsin(), PriceType.LOW.toString(), lowPrice, "USD", volume));
                    list.add(new StockPrice("alphavantage", interval.toString(), date, share.getIsin(), PriceType.HIGH.toString(), highPrice, "USD", volume));
                    list.add(new StockPrice("alphavantage", interval.toString(), date, share.getIsin(), PriceType.CLOSE.toString(), closePrice, "USD", volume));
                }
            } else {
                throw new IllegalArgumentException("Unknown interval: " + interval);
            }
            return list;
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }
}
