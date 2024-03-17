package com.fuijitsu.thomas.internship.service;

import com.fuijitsu.thomas.internship.model.Observation;
import com.fuijitsu.thomas.internship.repository.CityRepository;
import com.fuijitsu.thomas.internship.repository.ObservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Service that handles loading and saving observations, as well as parsing the XML.
 */
@Service
public class ObservationService {

    private final CityRepository cityRepository;
    private final ObservationRepository observationRepository;
    private Set<String> stationsToMonitor;

    Logger logger = LoggerFactory.getLogger(ObservationService.class);

    @Autowired
    public ObservationService(CityRepository cityRepository, ObservationRepository observationRepository) {
        this.cityRepository = cityRepository;
        this.observationRepository = observationRepository;
        calculateStationsToMonitor();
    }

    /**
     * If we wish to update which stations to follow
     */
    private void calculateStationsToMonitor() {
        this.stationsToMonitor = cityRepository.getStationsToMonitor();
    }

    /**
     * Loads data from the weather url
     * @return List of observation objects that we listen to
     * @throws Exception If something goes wrong during reading
     */
    public Set<Observation> loadData() throws Exception {
        URL url = new URI("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php").toURL();
        Set<Observation> observations = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(url.openStream());
        long timestamp = Long.parseLong(doc.getDocumentElement().getAttribute("timestamp"));
        NodeList stations = doc.getElementsByTagName("station");
        for (int i = 0; i < stations.getLength(); i++) {
            Element station = (Element) stations.item(i);

            String stationName = station.getElementsByTagName("name").item(0).getTextContent();
            if(!stationsToMonitor.contains(stationName)) continue;
            String phenomenon = station.getElementsByTagName("phenomenon").item(0).getTextContent();
            float windSpeed = Float.parseFloat(station.getElementsByTagName("windspeed").item(0).getTextContent());
            float airTemperature = Float.parseFloat(station.getElementsByTagName("airtemperature").item(0).getTextContent());
            Observation observation = new Observation(stationName, airTemperature, timestamp, phenomenon, windSpeed);
            observations.add(observation);
            logger.debug("Added observation for: " + stationName);
        }
        return observations;
    }

    /**
     * Method to save the observations that we loaded to the DB
     * @param observations observations we wish to save
     */
    public void saveObservations(Set<Observation> observations) {
        observationRepository.saveAll(observations);
        logger.debug("Saved newest observations.");
    }
}
