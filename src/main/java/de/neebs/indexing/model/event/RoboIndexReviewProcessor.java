package de.neebs.indexing.model.event;

import de.neebs.indexing.model.index.IndexVariationSecurity;
import de.neebs.indexing.model.index.IndexVariationTimespan;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RoboIndexReviewProcessor implements IndexReviewProcessor {
    @Override
    public Date getNextReviewDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date firstReviewDate = calendar.getTime();
        System.out.println("First review date: " + firstReviewDate);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date secondReviewDate = calendar.getTime();
        System.out.println("Second review date: " + secondReviewDate);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date thirdReviewDate = calendar.getTime();
        System.out.println("Third review date: " + thirdReviewDate);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date fourthReviewDate = calendar.getTime();
        System.out.println("Fourth review date: " + fourthReviewDate);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date nextYearReviewDate = calendar.getTime();
        System.out.println("Fifth review date: " + nextYearReviewDate);
        if (date.before(firstReviewDate)) {
            return firstReviewDate;
        } else if (date.before(secondReviewDate)) {
            return secondReviewDate;
        } else if (date.before(thirdReviewDate)) {
            return thirdReviewDate;
        } else if (date.before(fourthReviewDate)) {
            return fourthReviewDate;
        } else {
            return nextYearReviewDate;
        }
    }

    @Override
    public List<IndexVariationSecurity> initialPeriod(IndexVariationTimespan timespan) {
        List<IndexVariationSecurity> list = new ArrayList<>();
        list.add(new IndexVariationSecurity(timespan.getTimespanId(), "US5949181045", 1d));
        list.add(new IndexVariationSecurity(timespan.getTimespanId(), "US88554D2053", 1d));
        return list;
    }

    @Override
    public List<IndexVariationSecurity> nextPeriod(IndexVariationTimespan nextTimespan, List<IndexVariationSecurity> lastSecurities) {
        return lastSecurities.stream().map(f -> new IndexVariationSecurity(nextTimespan.getTimespanId(), f.getIsin(), f.getFraction())).collect(Collectors.toList());
    }
}
