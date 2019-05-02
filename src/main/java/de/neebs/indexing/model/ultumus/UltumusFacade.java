package de.neebs.indexing.model.ultumus;

import de.neebs.indexing.model.common.Currency;
import de.neebs.indexing.model.common.CurrencyRepository;
import de.neebs.indexing.model.index.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UltumusFacade {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${ultumus.summaryUrl}")
    private String summaryUrl;

    @Value("${ultumus.apiKey}")
    private String apiKey;

    @Autowired
    private IndexVariationRepository indexVariationRepository;

    @Autowired
    private IndexMasterdataRepository indexMasterdataRepository;

    @Autowired
    private IndexVariationTimespanRepository indexVariationTimespanRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private static final String VARIATION_NAME = "Ultumus";

    public void retrieveSummary() {
        List<Currency> currencies = currencyRepository.findAll();
        Currency USD = currencies.stream().filter(f -> "USD".equals(f.getIso4217())).findFirst().orElse(null);
        ResponseEntity<UltumusSummary> response = restTemplate.getForEntity(summaryUrl + "?api-key=" + apiKey, UltumusSummary.class);
        System.out.println(response.getBody());
        for (UltumusSummaryEntry entry : response.getBody().getData()) {
            try {
                Optional<IndexMasterdata> optMasterdata = indexMasterdataRepository.findByName(entry.getDescription());
                IndexMasterdata masterdata = optMasterdata.orElseGet(() -> indexMasterdataRepository.save(
                        new IndexMasterdata(null, entry.getDescription(), USD.getIso4217(), null))
                );
                System.out.println(masterdata);
                Optional<IndexVariation> optIndexVariation = indexVariationRepository.findByMasterdataIdAndName(
                        masterdata.getMasterdataId(), VARIATION_NAME);
                IndexVariation indexVariation = optIndexVariation.orElseGet(() -> indexVariationRepository.save(
                        new IndexVariation(VARIATION_NAME, masterdata.getMasterdataId()))
                );
                String detailUrl = entry.getHref().substring(0, entry.getHref().lastIndexOf("/") + 1);
                String strDate = entry.getHref().substring(entry.getHref().lastIndexOf("/") + 1);
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
                System.out.println(date);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date tomorrow = calendar.getTime();
                Optional<IndexVariationTimespan> optTimespan = indexVariationTimespanRepository.findByVariationIdAndValidFrom(
                        indexVariation.getVariationId(), date);
                IndexVariationTimespan timespan = optTimespan.orElseGet(() -> indexVariationTimespanRepository.save(
                        new IndexVariationTimespan(indexVariation.getVariationId(), date, tomorrow))
                );
                System.out.println(timespan);
                ResponseEntity<String> response2 = restTemplate.getForEntity(detailUrl +
                        new SimpleDateFormat("yyyy-MM-dd").format(date) + "?api-key=" + apiKey, String.class);
                System.out.println(response2.getBody());
            } catch (ParseException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
