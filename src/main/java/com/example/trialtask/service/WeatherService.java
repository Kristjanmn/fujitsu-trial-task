package com.example.trialtask.service;

import com.example.trialtask.configuration.ApplicationProperties;
import com.example.trialtask.entity.Weather;
import com.example.trialtask.entity.WeatherStation;
import com.example.trialtask.model.Observations;
import com.example.trialtask.repository.WeatherRepository;
import com.example.trialtask.repository.WeatherStationRepository;
import com.example.trialtask.util.Utils;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService implements IWeatherService {
    private final ApplicationProperties applicationProperties;
    private final WeatherRepository weatherRepository;
    private final WeatherStationRepository weatherStationRepository;

    /**
     * Update weather at specified time using cron.
     * Time can be changed in ApplicationProperties.
     * Default is "0 15 * * * *", meaning it triggers
     * every hour at 15 minutes.
     */
    @Override
    @Scheduled(cron = "${application.weatherCron}")
    public void updateWeatherData() {
        RestClient client = RestClient.create();
        String response = client.get()
                .uri(applicationProperties.getWeatherDataUri())
                .retrieve()
                .body(String.class);

        try {
            XmlMapper xmlMapper = new XmlMapper();
            Observations observations = xmlMapper.readValue(response, Observations.class);
            List<String> stationNames = new ArrayList<>();
            for (WeatherStation station : weatherStationRepository.findAll()) {
                stationNames.add(station.getStation());
            }

            for (Observations.Station station : observations.getStations()) {
                if (Utils.anyStringEquals(station.getName(), stationNames.toArray(new String[0]))) {
                    Weather weather = Weather.builder().station(station.getName())
                            .wmoCode(station.getWmoCode())
                            .airTemp(station.getAirTemperature())
                            .windSpeed(station.getWindSpeed())
                            .weatherPhenomenon(station.getPhenomenon())
                            .timestamp(observations.getTimestamp()).build();
                    weatherRepository.save(weather);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get most recent weather data from database.
     *
     * @param city
     * @return
     * @throws Exception
     */
    @Override
    public Weather getLatestWeather(String city) throws Exception {
        if (weatherRepository.count() == 0) this.updateWeatherData();
        String station = this.getStationByCity(city);
        Optional<Weather> optWeather = weatherRepository.findFirstByStationOrderByIdDesc(station);
        if (optWeather.isEmpty()) throw new Exception("Could not find weather for "+city);
        return optWeather.get();
    }

    /**
     * Get all cities that have weather stations.
     *
     * @return
     */
    @Override
    public List<String> getAllCities() {
        List<String> list = new ArrayList<>();
        for (WeatherStation station : weatherStationRepository.findAll()) {
            list.add(station.getCity());
        }
        return list;
    }

    /**
     * Get all weather stations from database.
     *
     * @return
     */
    @Override
    public List<String> getAllStations() {
        List<String> list = new ArrayList<>();
        for (WeatherStation station : weatherStationRepository.findAll()) {
            list.add(station.getStation());
        }
        return list;
    }

    /**
     * Get weather station name for specified city.
     *
     * @param city
     * @return
     * @throws Exception
     */
    @Override
    public String getStationByCity(String city) throws Exception {
        Optional<WeatherStation> optStation = weatherStationRepository.findFirstByCityEqualsIgnoreCaseOrderByIdDesc(city);
        if (optStation.isEmpty()) throw new Exception("Could not find station for "+city);
        return optStation.get().getStation();
    }

    @Override
    public String getStationListAsString() {
        StringBuilder sb = new StringBuilder();
        for (WeatherStation station : weatherStationRepository.findAll()) {
            sb.append("{city=").append(station.getCity());
            sb.append(", station=").append(station.getStation()).append("}");
        }
        return sb.toString();
    }

    @Override
    public boolean addWeatherStation(String city, String station) {
        Optional<WeatherStation> optStation = weatherStationRepository.findFirstByCityEqualsIgnoreCaseAndStationEqualsIgnoreCase(city, station);
        if (optStation.isPresent()) return false;
        try {
            weatherStationRepository.save(WeatherStation.builder().city(city).station(station).build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean removeWeatherStation(String city, String station) {
        Optional<WeatherStation> optStation = weatherStationRepository.findFirstByCityEqualsIgnoreCaseAndStationEqualsIgnoreCase(city, station);
        if (optStation.isEmpty()) return false;
        try {
            weatherStationRepository.delete(optStation.get());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
