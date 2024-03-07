package com.example.trialtask.service;

import com.example.trialtask.entity.Weather;

import java.util.List;

public interface IWeatherService {
    void updateWeatherData();
    Weather getLatestWeather(String city) throws Exception;
    List<String> getAllCities();
    List<String> getAllStations();
    String getStationByCity(String city) throws Exception;
    String getStationListAsString();
    boolean addWeatherStation(String city, String station);
    boolean removeWeatherStation(String city, String station);
}
