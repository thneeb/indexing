package de.neebs.indexing.model.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TimeSeriesFacade {
    @Autowired
    private TimeSeriesRepository timeSeriesRepository;

    public Date getMaxDate() {
        Optional<TimeSeries> opt = timeSeriesRepository.findTopByOrderByTheDateDesc();
        if (opt.isPresent()) {
            return opt.get().getTheDate();
        } else {
            throw new IllegalStateException("Cannot evaluate maximum date");
        }
    }

    public Date getMinDate() {
        Optional<TimeSeries> opt = timeSeriesRepository.findTopByOrderByTheDate();
        if (opt.isPresent()) {
            return opt.get().getTheDate();
        } else {
            throw new IllegalStateException("Cannot evaluate maximum date");
        }
    }
}
