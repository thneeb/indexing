package de.neebs.indexing.model.event;

import de.neebs.indexing.model.index.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.security.PrivilegedActionException;
import java.util.*;

@Component
public class EventFacade implements ApplicationContextAware {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private CorporateEventDao corporateEventDao;

    @Autowired
    private CorporateEventRepository corporateEventRepository;

    @Autowired
    private IndexVariationTimespanRepository indexVariationTimespanRepository;

    @Autowired
    private IndexVariationSecurityRepository indexVariationSecurityRepository;

    @Autowired
    private IndexEventProcessorRepository indexEventProcessorRepository;

    @Autowired
    private IndexFacade indexFacade;

    @Autowired
    private RoboIndexReviewProcessor indexReviewProcessor;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void applyEvents(IndexVariation variation, Date fromDate, Date toDate) {
        try {
            // Wer muss die Corporate Events für diesen Index verarbeiten?
            Map<Class, EventProcessor> processorMap = createProcessorMap(variation.getMasterdataId());
            // hier muss noch ein Lookup her
            IndexReviewProcessor reviewProcessor = indexReviewProcessor;
            // Initiale Timespan erzeugen
            IndexVariationTimespan timespan = new IndexVariationTimespan(variation.getVariationId(), fromDate, toDate);
            timespan = indexVariationTimespanRepository.save(timespan);
            List<IndexVariationSecurity> securities = reviewProcessor.initialPeriod(timespan);
            List<IndexVariationSecurity> savedSecurities = new ArrayList<>();
            indexVariationSecurityRepository.saveAll(securities).forEach(savedSecurities::add);
            securities = savedSecurities;

            Date actualDate = fromDate;
            while (actualDate.before(toDate)) {
                Date reviewDate = reviewProcessor.getNextReviewDate(actualDate);
                List<CorporateEvent> events = new ArrayList<>();
                for (IndexVariationSecurity ivs : securities) {
                    corporateEventRepository.findEventsInTimespan(ivs.getIsin(), actualDate, reviewDate.before(toDate) ? reviewDate : toDate).forEach(events::add);
                }
                // Alles sortieren, damit wir die Zeitscheiben gut schneiden können
                events.sort(Comparator.comparing(CorporateEvent::getEffectiveDate));

                for (CorporateEvent ce : events) {
                    if (actualDate != ce.getEffectiveDate()) {
                        // eine neue Zeitscheibe erzeugen
                        IndexVariationTimespan newTimespan = new IndexVariationTimespan(variation.getVariationId(), actualDate, timespan.getValidTo());
                        newTimespan = indexFacade.insertTimespan(newTimespan, securities, true);
                        securities = new ArrayList<>();
                        indexVariationSecurityRepository.findByTimespanId(newTimespan.getTimespanId()).forEach(securities::add);
                        actualDate = ce.getEffectiveDate();
                        timespan = newTimespan;
                    }
                    EventProcessor processor = processorMap.get(ce.getClass());
                    securities = processor.execute(timespan, securities, ce);
                    savedSecurities = new ArrayList<>();
                    indexVariationSecurityRepository.saveAll(securities).forEach(savedSecurities::add);
                    securities = savedSecurities;
                }

                actualDate = reviewDate;

                if (reviewDate.before(toDate)) {
                    timespan = new IndexVariationTimespan(variation.getVariationId(), reviewDate, timespan.getValidTo());
                    timespan = indexFacade.insertTimespan(timespan, securities, true);
                    securities = reviewProcessor.nextPeriod(timespan, securities);
                    savedSecurities = new ArrayList<>();
                    indexVariationSecurityRepository.saveAll(securities).forEach(savedSecurities::add);
                    securities = savedSecurities;
                }
            }
        } catch (PrivilegedActionException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<Class, EventProcessor> createProcessorMap(int masterdataId) {
        Map<Class, EventProcessor> map = new HashMap<>();
        for (IndexEventProcessor iep : indexEventProcessorRepository.findByMasterdataId(masterdataId)) {
            try {
                Class eventClass = Class.forName(iep.getEvent());
                EventProcessor processor = applicationContext.getBean(iep.getProcessor(), EventProcessor.class);
                map.put(eventClass, processor);
            } catch (ClassNotFoundException e) {
                LOG.error("No class found for defined event", e);
            }
        }

        return map;
    }
}
