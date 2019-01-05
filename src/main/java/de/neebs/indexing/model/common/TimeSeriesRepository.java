package de.neebs.indexing.model.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TimeSeriesRepository extends CrudRepository<TimeSeries, Date> {
    Optional<TimeSeries> findTopByOrderByTheDate();
    Optional<TimeSeries> findTopByOrderByTheDateDesc();
}
