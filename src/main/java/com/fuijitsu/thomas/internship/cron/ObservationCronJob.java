package com.fuijitsu.thomas.internship.cron;

import com.fuijitsu.thomas.internship.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Cron Job class, sets up the cronjob which uses observation service
 */
@Component
public class ObservationCronJob {


    private ObservationService observationService;


    @Autowired
    public ObservationCronJob(ObservationService observationService) {
        this.observationService = observationService;
    }

    /**
     * The "${intern.cron.weather}" refers to it in application.properties, which is a cron trigger
     */
    @Scheduled(cron = "${intern.cron.weather}")
    public void queryStations() {
        try {
            observationService.saveObservations(observationService.loadData());
        } catch (Exception e) {
            System.out.println("Something strange happened during the loading of data from XML.");
        }
    }


}
