package com.nttdata.dtl.model.index;

import com.nttdata.dtl.model.common.TimeSeriesFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class IndexFacade {
    @Autowired
    private IndexVariationTimespanRepository indexVariationTimespanRepository;

    @Autowired
    private IndexVariationSecurityRepository indexVariationSecurityRepository;

    @Autowired
    private TimeSeriesFacade timeSeriesFacade;

    private <T extends Timespan> List<T> insertTimespan(T timespan, List<T> list, boolean force) throws PrivilegedActionException {
        List<T> result = new ArrayList<>();
        Date minDate = timeSeriesFacade.getMinDate();
        Date maxDate = timeSeriesFacade.getMaxDate();
        if (timespan.getValidFrom() == null) {
            timespan.setValidFrom(minDate);
        }
        if (timespan.getValidTo() == null) {
            timespan.setValidTo(maxDate);
        }
        if (timespan.getValidTo().before(timespan.getValidFrom())) {
            throw new IllegalArgumentException();
        }
        if (timespan.getValidFrom().equals(timespan.getValidTo())) {
            throw new IllegalArgumentException();
        }
        if (timespan.getValidFrom().equals(minDate) && timespan.getValidTo().equals(maxDate)) {
            if (!list.isEmpty()) {
                throw new IllegalArgumentException();
            }
        } else if (timespan.getValidFrom().equals(minDate)) {
            if (!force) {
                throw new PrivilegedActionException(null);
            }
            // falls der existierende Eintrag mit validFrom = minDate ein validTo hat, dass nach dem validTo des
            // neuen Eintrags liegt, dann wird das validForm des existierenden Eintrags auf das validTo des neuen
            // Eintrags gesetzt und der neue Eintrag wird übernommen, ansonsten würde ein Eintrag gelöscht und das
            // darf hier nicht passieren CONFLICT
            for (T ivt : list) {
                if (ivt.getValidFrom().equals(minDate)) {
                    if (ivt.getValidTo().after(timespan.getValidTo())) {
                        ivt.setValidFrom(timespan.getValidTo());
                        result.add(ivt);
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        } else if (timespan.getValidTo().equals(maxDate)) {
            // wenn der bestehende Eintrag mit validTo = maxDate einen validFrom hat, der vor dem übermittelten
            // validFrom liegt, dann wird der Eintrag hinzugefügt, sonst nicht
            for (T ivt : list) {
                if (ivt.getValidTo().equals(maxDate)) {
                    if (ivt.getValidFrom().before(timespan.getValidFrom())) {
                        ivt.setValidTo(timespan.getValidFrom());
                        result.add(ivt);
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        } else {
            // validFrom und validTo sind gesetzt
            // wenn es einen Eintrag gibt, der durch den neuen Eintrag komplett überschrieben würde, dann wird der
            // der Request abgebrochen
            for (T ivt : list) {
                if (ivt.getValidFrom().after(timespan.getValidFrom()) && ivt.getValidTo().before(timespan.getValidTo())) {
                    throw new IllegalArgumentException();
                }
            }
            if (!force) {
                throw new PrivilegedActionException(null);
            }
            // wenn ein Eintrag existiert, in dem der bestehende Eintrag vollständig aufgeht, dann wird der
            // bestehende Eintrag aufgeteilt, in ein Stück, was vor dem neuen Eintrag liegt und eines, was dahinter
            // liegt.
            for (T ivt : list) {
                if (ivt.getValidFrom().before(timespan.getValidFrom()) && ivt.getValidTo().after(timespan.getValidTo())) {
                    Date endDate = ivt.getValidTo();
                    ivt.setValidTo(timespan.getValidFrom());
                    result.add(ivt);
                    T ivt2 = (T)ivt.clone();
                    ivt2.setValidFrom(timespan.getValidTo());
                    ivt2.setValidTo(endDate);
                    result.add(ivt2);
                    break;
                }
            }
            // sind zwei Einträge davon betroffen, dann werden beide Einträge so angepasst, dass der neue Eintrag
            // überschneidungsfrei dazwischen liegt. Es darf dabei kein bestehender Eintrag wegfallen.
            for (T ivt : list) {
                if (ivt.getValidFrom().before(timespan.getValidFrom())&& ivt.getValidTo().after(timespan.getValidFrom())&& ivt.getValidTo().before(timespan.getValidTo())) {
                    ivt.setValidTo(timespan.getValidFrom());
                    result.add(ivt);
                } else if (ivt.getValidFrom().after(timespan.getValidFrom())&& ivt.getValidFrom().before(timespan.getValidTo()) && ivt.getValidTo().after(timespan.getValidTo())) {
                    ivt.setValidFrom(timespan.getValidTo());
                    result.add(ivt);
                }
            }
        }
        return result;
    }

    @Transactional
    public IndexVariationTimespan insertTimespan(IndexVariationTimespan timespan, List<IndexVariationSecurity> securities, boolean force) throws PrivilegedActionException {
/*
        Date minDate = timeSeriesFacade.getMinDate();
        Date maxDate = timeSeriesFacade.getMaxDate();
        if (timespan.getValidFrom() == null) {
            timespan.setValidFrom(minDate);
        }
        if (timespan.getValidTo() == null) {
            timespan.setValidTo(maxDate);
        }
        if (timespan.getValidTo().before(timespan.getValidFrom())) {
            throw new IllegalArgumentException();
        }
        if (timespan.getValidFrom().equals(timespan.getValidTo())) {
            throw new IllegalArgumentException();
        }
        if (timespan.getValidFrom().equals(minDate) && timespan.getValidTo().equals(maxDate)) {
            if (indexVariationTimespanRepository.findByVariationIdOrderByValidFrom(timespan.getVariationId()).iterator().hasNext()) {
                throw new IllegalArgumentException();
            }
        } else if (timespan.getValidFrom().equals(minDate)) {
            if (!force) {
                throw new PrivilegedActionException(null);
            }
            // falls der existierende Eintrag mit validFrom = minDate ein validTo hat, dass nach dem validTo des
            // neuen Eintrags liegt, dann wird das validForm des existierenden Eintrags auf das validTo des neuen
            // Eintrags gesetzt und der neue Eintrag wird übernommen, ansonsten würde ein Eintrag gelöscht und das
            // darf hier nicht passieren CONFLICT
            for (IndexVariationTimespan ivt : indexVariationTimespanRepository.findByVariationIdOrderByValidFrom(timespan.getVariationId())) {
                if (ivt.getValidFrom().equals(minDate)) {
                    if (ivt.getValidTo().after(timespan.getValidTo())) {
                        ivt.setValidFrom(timespan.getValidTo());
                        indexVariationTimespanRepository.save(ivt);
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        } else if (timespan.getValidTo().equals(maxDate)) {
            // wenn der bestehende Eintrag mit validTo = maxDate einen validFrom hat, der vor dem übermittelten
            // validFrom liegt, dann wird der Eintrag hinzugefügt, sonst nicht
            for (IndexVariationTimespan ivt : indexVariationTimespanRepository.findByVariationIdOrderByValidFrom(timespan.getVariationId())) {
                if (ivt.getValidTo().equals(maxDate)) {
                    if (ivt.getValidFrom().before(timespan.getValidFrom())) {
                        ivt.setValidTo(timespan.getValidFrom());
                        indexVariationTimespanRepository.save(ivt);
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        } else {
            // validFrom und validTo sind gesetzt
            // wenn es einen Eintrag gibt, der durch den neuen Eintrag komplett überschrieben würde, dann wird der
            // der Request abgebrochen
            for (IndexVariationTimespan ivt : indexVariationTimespanRepository.findByVariationIdOrderByValidFrom(timespan.getVariationId())) {
                if (ivt.getValidFrom().after(timespan.getValidFrom()) && ivt.getValidTo().before(timespan.getValidTo())) {
                    throw new IllegalArgumentException();
                }
            }
            if (!force) {
                throw new PrivilegedActionException(null);
            }
            // wenn ein Eintrag existiert, in dem der bestehende Eintrag vollständig aufgeht, dann wird der
            // bestehende Eintrag aufgeteilt, in ein Stück, was vor dem neuen Eintrag liegt und eines, was dahinter
            // liegt.
            for (IndexVariationTimespan ivt : indexVariationTimespanRepository.findByVariationIdOrderByValidFrom(timespan.getVariationId())) {
                if (ivt.getValidFrom().before(timespan.getValidFrom()) && ivt.getValidTo().after(timespan.getValidTo())) {
                    Date endDate = ivt.getValidTo();
                    ivt.setValidTo(timespan.getValidFrom());
                    indexVariationTimespanRepository.save(ivt);
                    IndexVariationTimespan ivt2 = new IndexVariationTimespan();
                    ivt2.setVariationId(ivt.getVariationId());
                    ivt2.setValidFrom(timespan.getValidTo());
                    ivt2.setValidTo(endDate);
                    indexVariationTimespanRepository.save(ivt2);
                    break;
                }
            }
            // sind zwei Einträge davon betroffen, dann werden beide Einträge so angepasst, dass der neue Eintrag
            // überschneidungsfrei dazwischen liegt. Es darf dabei kein bestehender Eintrag wegfallen.
            for (IndexVariationTimespan ivt : indexVariationTimespanRepository.findByVariationIdOrderByValidFrom(timespan.getVariationId())) {
                if (ivt.getValidFrom().before(timespan.getValidFrom())&& ivt.getValidTo().after(timespan.getValidFrom())&& ivt.getValidTo().before(timespan.getValidTo())) {
                    ivt.setValidTo(timespan.getValidFrom());
                    indexVariationTimespanRepository.save(ivt);
                } else if (ivt.getValidFrom().after(timespan.getValidFrom())&& ivt.getValidFrom().before(timespan.getValidTo()) && ivt.getValidTo().after(timespan.getValidTo())) {
                    ivt.setValidFrom(timespan.getValidTo());
                    indexVariationTimespanRepository.save(ivt);
                }
            }
        }
        */

        List<IndexVariationTimespan> list = new ArrayList<>();
        indexVariationTimespanRepository.findByVariationIdOrderByValidFrom(timespan.getVariationId()).forEach(list::add);
        List<IndexVariationTimespan> save = insertTimespan(timespan, list, force);
        indexVariationTimespanRepository.saveAll(save);
        IndexVariationTimespan timespan2 = indexVariationTimespanRepository.save(timespan);
        securities.forEach(f -> f.setTimespanId(timespan2.getTimespanId()));
        indexVariationSecurityRepository.saveAll(securities);
        return timespan2;
    }

    public IndexVariationProvider insertProvider(IndexVariationProvider entity) {
        return null;
    }
}
