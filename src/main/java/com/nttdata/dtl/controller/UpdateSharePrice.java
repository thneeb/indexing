package com.nttdata.dtl.controller;

import com.nttdata.dtl.model.Provider;
import com.nttdata.dtl.model.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class UpdateSharePrice {
    @Autowired
    private ProviderRepository providerRepository;

    @Scheduled(cron="*/5 * * * * *")
    public void schedule() {
        for (Provider provider : providerRepository.findAll()) {
            Calendar oneMinuteAgo = new GregorianCalendar();
            oneMinuteAgo.add(Calendar.MINUTE, -1);
//            System.out.println("Schedular run");
        }
    }
}
