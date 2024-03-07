package com.example.trialtask.service;

import com.example.trialtask.entity.Weather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WeatherServiceTest {
    @Autowired
    private IWeatherService weatherService;

    @Test
    void getLatestWeather() {
        String city = "Tartu";
        String expectedStation = "Tartu-Tõravere";
        assertDoesNotThrow(() -> {
            Weather weather = weatherService.getLatestWeather(city);
            assertEquals(expectedStation, weather.getStation());
        });
    }

    @Test
    void getLatestWeather_exception() {
        String city = "Viljandi";
        String expectedMessage = "Could not find station for Viljandi";
        Exception exception = assertThrows(Exception.class, () -> weatherService.getLatestWeather(city));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getAllCities() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Tallinn");
        expectedList.add("Tartu");
        expectedList.add("Pärnu");
        List<String> actualList = weatherService.getAllCities();
        assert actualList.containsAll(expectedList);
    }

    @Test
    void getAllStations() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Tallinn-Harku");
        expectedList.add("Tartu-Tõravere");
        expectedList.add("Pärnu");
        List<String> actualList = weatherService.getAllStations();
        assert actualList.containsAll(expectedList);
    }

    @Test
    void getStationByCity() {
        String city = "Tartu";
        String expectedResult = "Tartu-Tõravere";
        assertDoesNotThrow(() -> {
            String actualResult = weatherService.getStationByCity(city);
            assertEquals(expectedResult, actualResult);
        });
    }

    @Test
    void getStationByCity_exception() {
        String city = "Haapsalu";
        String expectedMessage = "Could not find station for Haapsalu";
        Exception exception = assertThrows(Exception.class, () -> weatherService.getStationByCity(city));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getWeatherStations() {
        String stationsStr = weatherService.getStationListAsString();
        String expectedStation = "{city=Tallinn, station=Tallinn-Harku}";
        assertTrue(stationsStr.contains(expectedStation));
    }

    @Test
    void addWeatherStation_success() {
        String city = "Narva";
        String station = "Narva-Jõesuu";
        assertTrue(weatherService.addWeatherStation(city, station));
    }

    @Test
    void addWeatherStation_failure() {
        // Fails because city - station pair exists by default
        String city = "Pärnu";
        String station = "Pärnu";
        assertFalse(weatherService.addWeatherStation(city, station));
    }

    @Test
    void removeWeatherStation_success() {
        String city = "Tallinn";
        String station = "Tallinn-Harku";
        assertTrue(weatherService.removeWeatherStation(city, station));
    }

    @Test
    void removeWeatherStation_failure() {
        // Fails because city - station pair does not exist by default
        String city = "Narva";
        String station = "Narva";
        assertFalse(weatherService.removeWeatherStation(city, station));
    }
}
