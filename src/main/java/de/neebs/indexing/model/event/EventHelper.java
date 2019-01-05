package de.neebs.indexing.model.event;

import de.neebs.indexing.model.index.IndexVariationProviderSecurity;
import de.neebs.indexing.model.index.IndexVariationProviderSecurityRepository;
import de.neebs.indexing.model.provider.OpenHighCloseLow;
import de.neebs.indexing.model.provider.SharePrice;
import de.neebs.indexing.model.provider.SharePriceKey;
import de.neebs.indexing.model.provider.SharePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@Component
public class EventHelper {
    @Autowired
    private IndexVariationProviderSecurityRepository indexVariationProviderSecurityRepository;

    @Autowired
    private SharePriceRepository sharePriceRepository;

    public SharePrice getSharePrice(int variationId, String isin, Date date) {
        SharePrice sharePrice = null;
        for (IndexVariationProviderSecurity ivps : indexVariationProviderSecurityRepository.findActualProvider(
                variationId, isin, date)) {
            Optional<SharePrice> optionalSharePrice = sharePriceRepository.findById(new SharePriceKey(
                    ivps.getProviderQueryId(), ivps.getIsin(), ivps.getSymbol(), date, OpenHighCloseLow.CLOSE));
            if (optionalSharePrice.isPresent()) {
                sharePrice = optionalSharePrice.get();
                break;
            }
        }
        return sharePrice;
    }

    public Date getDayBefore(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }
}
