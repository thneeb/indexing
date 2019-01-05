package de.neebs.indexing.model.alphavantage;

import de.neebs.indexing.model.common.Currency;
import de.neebs.indexing.model.common.SecuritySymbol;
import de.neebs.indexing.model.common.SecurityRepository;
import de.neebs.indexing.model.provider.*;
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
    private SecurityRepository securityRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Value("${alphavantage.apikey}")
    private String apikey;

    @Override
    public String getName() {
        return "Alphavantage";
    }

    private boolean isFullLoad(ProviderQuery providerQuery, SecuritySymbol securitySymbol) {
        if (!sharePriceRepository.existsByProviderQueryIdAndIsinAndSymbol(
                providerQuery.getProviderQueryId(), securitySymbol.getIsin(), securitySymbol.getSymbol())) {
            return true;
        } else {
            Date lastQuote = sharePriceRepository.getLastQuote(providerQuery.getProviderQueryId(), securitySymbol.getIsin(), securitySymbol.getSymbol());
            if (providerQuery.getName().contains("INTRADAY")) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(lastQuote);
                calendar.add(Calendar.MINUTE, 80);
                return new Date().after(calendar.getTime());
            } else if (providerQuery.getName().contains("DAILY")) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(lastQuote);
                calendar.add(Calendar.DAY_OF_YEAR, 80);
                return new Date().after(calendar.getTime());
            } else {
                return true;
            }
        }
    }

    @Override
    public void execute(ProviderQuery providerQuery, SecuritySymbol symbol, Date lastRun) {
        try {
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
            sb.append(symbol.getSymbol());
            sb.append("&outputsize=");
            if (isFullLoad(providerQuery, symbol)) {
                sb.append("full");
            } else {
                sb.append("compact");
            }
            sb.append("&apikey=");
            sb.append(apikey);
            LOG.info(new String(sb));
            ResponseEntity<Map> response = restTemplate.getForEntity(new String(sb), Map.class);
            String errorMessage = (String) response.getBody().get("Error Message");
            String note = (String) response.getBody().get("Note");
            if (errorMessage != null) {
                LOG.error(errorMessage);
            } else if (note != null) {
                LOG.error(note);
            } else {
                Optional<String> key = response.getBody().keySet().stream().filter(f -> !"Meta Data".equals(f)).findFirst();
                if (!key.isPresent()) {
                    throw new IllegalStateException("Cannot find non Meta Data key");
                }
                Map<String, Map<String, String>> map = (Map<String, Map<String, String>>) response.getBody().get(key.get());
                for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
                    Date date = df.parse(entry.getKey());
                    Map<String, String> price = entry.getValue();
                    double openPrice = Double.parseDouble(price.get("1. open"));
                    double highPrice = Double.parseDouble(price.get("2. high"));
                    double lowPrice = Double.parseDouble(price.get("3. low"));
                    double closePrice = Double.parseDouble(price.get("4. close"));
                    long volume = Long.parseLong(price.get("5. volume"));
                    sharePriceRepository.save(new SharePrice(providerQuery.getProviderQueryId(), symbol.getIsin(), symbol.getSymbol(), date, OpenHighCloseLow.OPEN, openPrice, volume));
                    sharePriceRepository.save(new SharePrice(providerQuery.getProviderQueryId(), symbol.getIsin(), symbol.getSymbol(), date, OpenHighCloseLow.HIGH, highPrice, volume));
                    sharePriceRepository.save(new SharePrice(providerQuery.getProviderQueryId(), symbol.getIsin(), symbol.getSymbol(), date, OpenHighCloseLow.CLOSE, lowPrice, volume));
                    sharePriceRepository.save(new SharePrice(providerQuery.getProviderQueryId(), symbol.getIsin(), symbol.getSymbol(), date, OpenHighCloseLow.LOW, closePrice, volume));
                }
                LOG.info("Retrieved time series from " + providerQuery.getName() + " for " + symbol.getIsin() + "(" + symbol.getSymbol() + ")");
            }
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
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
            String errorMessage = (String) response.getBody().get("Error Message");
            String note = (String) response.getBody().get("Note");
            if (errorMessage != null) {
                LOG.error(errorMessage);
            } else if (note != null) {
                LOG.error(note);
            } else {
                Optional<String> key = response.getBody().keySet().stream().filter(f -> !"Meta Data".equals(f)).findFirst();
                if (!key.isPresent()) {
                    throw new IllegalStateException("Cannot find non Meta Data key");
                }
                Map<String, Map<String, String>> map = (Map<String, Map<String, String>>) response.getBody().get(key.get());
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
            }
            LOG.info("Retrieved time series from " + providerQuery.getName() + " for " + fromCurrency.getIso4217() + "2" + toCurrency.getIso4217());
            return true;
        } catch(ParseException e){
            throw new IllegalStateException(e);
        }
    }

    private boolean isFullLoad(ProviderQuery providerQuery, Currency fromCurrency, Currency toCurrency) {
        return true;
    }
}
