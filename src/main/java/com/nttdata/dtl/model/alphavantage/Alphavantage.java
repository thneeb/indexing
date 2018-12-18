package com.nttdata.dtl.model.alphavantage;

import com.nttdata.dtl.model.common.Currency;
import com.nttdata.dtl.model.common.Share;
import com.nttdata.dtl.model.common.ShareRepository;
import com.nttdata.dtl.model.provider.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Alphavantage implements TimeSeriesProvider {
    private static final Logger LOG = LoggerFactory.getLogger(Alphavantage.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SharePriceRepository sharePriceRepository;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Value("${alphavantage.apikey}")
    private String apikey;

    @Override
    public String getName() {
        return "Alphavantage";
    }

    private boolean isFullLoad(ProviderQuery providerQuery, Share share) {
        if (!sharePriceRepository.existsByProviderQueryIdAndIsin(providerQuery.getProviderQueryId(), share.getIsin())) {
            return true;
        } else {
            Date lastQuote = sharePriceRepository.getLastQuote(providerQuery.getProviderQueryId(), share.getIsin());
            if (providerQuery.getName().contains("INTRADAY")) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(lastQuote);
                calendar.add(Calendar.MINUTE, 80);
                return new Date().before(calendar.getTime());
            } else if (providerQuery.getName().contains("DAILY")) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(lastQuote);
                calendar.add(Calendar.DAY_OF_YEAR, 80);
                return new Date().before(calendar.getTime());
            } else {
                return true;
            }
        }
    }

    @Override
    public boolean execute(ProviderQuery providerQuery, Share share, Date lastRun) {
        try {
            if (lastRun != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(lastRun);
                calendar.add(Calendar.MINUTE, 10);
                if (new Date().before(calendar.getTime())) {
                    LOG.info("Skipped " + providerQuery.getName() + " " + share.getSymbol());
                    return false;
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("https://www.alphavantage.co/query");
            sb.append("?function=");
            DateFormat df;
            if (providerQuery.getName().contains("INTRADAY")) {
                sb.append("TIME_SERIES_INTRADAY&interval=1min");
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else if (providerQuery.getName().contains("DAILY")) {
                sb.append("TIME_SERIES_DAILY");
                df = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                throw new IllegalStateException("Unsupported query type: " + providerQuery.getName());
            }
            sb.append("&symbol=");
            sb.append(share.getSymbol());
            sb.append("&outputsize=");
            if (isFullLoad(providerQuery, share)) {
                sb.append("full");
            } else {
                sb.append("compact");
            }
            sb.append("&apikey=");
            sb.append(apikey);
            LOG.info(new String(sb));
            ResponseEntity<Map> response = restTemplate.getForEntity(new String(sb), Map.class);
            Optional<String> key = response.getBody().keySet().stream().filter(f -> !"Meta Data".equals(f)).findFirst();
            if (!key.isPresent()) {
                throw new IllegalStateException("Cannot find non Meta Data key");
            }
            Map<String, Map<String, String>> map = (Map<String, Map<String, String>>)response.getBody().get(key.get());
            for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
                Date date = df.parse(entry.getKey());
                Map<String, String> price = entry.getValue();
                double openPrice = Double.parseDouble(price.get("1. open"));
                double highPrice = Double.parseDouble(price.get("2. high"));
                double lowPrice = Double.parseDouble(price.get("3. low"));
                double closePrice = Double.parseDouble(price.get("4. close"));
                long volume = Long.parseLong(price.get("5. volume"));
                sharePriceRepository.save(new SharePrice(providerQuery.getProviderQueryId(), share.getIsin(), date, OpenHighCloseLow.OPEN, openPrice, volume));
                sharePriceRepository.save(new SharePrice(providerQuery.getProviderQueryId(), share.getIsin(), date, OpenHighCloseLow.HIGH, highPrice, volume));
                sharePriceRepository.save(new SharePrice(providerQuery.getProviderQueryId(), share.getIsin(), date, OpenHighCloseLow.CLOSE, lowPrice, volume));
                sharePriceRepository.save(new SharePrice(providerQuery.getProviderQueryId(), share.getIsin(), date, OpenHighCloseLow.LOW, closePrice, volume));
            }
            return true;
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Share> availableShares() {
        List<Share> list = new ArrayList<>();
        shareRepository.findAll().forEach(f -> {
            if (f.getSymbol() != null) {
                list.add(f);
            }
        });
        return list;
    }

    @Override
    public boolean execute(ProviderQuery providerQuery, Currency fromCurrency, Currency toCurrency, Date lastRun) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("https://www.alphavantage.co/query");
            sb.append("?function=");
            DateFormat df;
            if (providerQuery.getName().contains("INTRADAY")) {
                sb.append("FX_INTRADAY&interval=1min");
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else if (providerQuery.getName().contains("DAILY")) {
                sb.append("FX_DAILY");
                df = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                throw new IllegalStateException("Unsupported query type: " + providerQuery.getName());
            }
            sb.append("&from_symbol=");
            sb.append(fromCurrency.getIso4217());
            sb.append("&to_symbol=");
            sb.append(toCurrency.getIso4217());
            sb.append("&outputsize=");
            if (isFullLoad(providerQuery, fromCurrency, toCurrency)) {
                sb.append("full");
            } else {
                sb.append("compact");
            }
            sb.append("&apikey=");
            sb.append(apikey);
            LOG.info(new String(sb));
            ResponseEntity<Map> response = restTemplate.getForEntity(new String(sb), Map.class);
            Optional<String> key = response.getBody().keySet().stream().filter(f -> !"Meta Data".equals(f)).findFirst();
            if (!key.isPresent()) {
                throw new IllegalStateException("Cannot find non Meta Data key");
            }
            Map<String, Map<String, String>> map = (Map<String, Map<String, String>>)response.getBody().get(key.get());
            for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
                Date date = df.parse(entry.getKey());
                Map<String, String> price = entry.getValue();
                double openPrice = Double.parseDouble(price.get("1. open"));
                double highPrice = Double.parseDouble(price.get("2. high"));
                double lowPrice = Double.parseDouble(price.get("3. low"));
                double closePrice = Double.parseDouble(price.get("4. close"));
                exchangeRateRepository.save(new ExchangeRate(fromCurrency.getIso4217(), toCurrency.getIso4217(),
                        providerQuery.getProviderQueryId(), date, OpenHighCloseLow.OPEN, openPrice));
                exchangeRateRepository.save(new ExchangeRate(fromCurrency.getIso4217(), toCurrency.getIso4217(),
                        providerQuery.getProviderQueryId(), date, OpenHighCloseLow.HIGH, highPrice));
                exchangeRateRepository.save(new ExchangeRate(fromCurrency.getIso4217(), toCurrency.getIso4217(),
                        providerQuery.getProviderQueryId(), date, OpenHighCloseLow.CLOSE, closePrice));
                exchangeRateRepository.save(new ExchangeRate(fromCurrency.getIso4217(), toCurrency.getIso4217(),
                        providerQuery.getProviderQueryId(), date, OpenHighCloseLow.LOW, lowPrice));
            }
            return true;
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean isFullLoad(ProviderQuery providerQuery, Currency fromCurrency, Currency toCurrency) {
        return true;
    }
}
