package com.example.trialtask.service;

import com.example.trialtask.entity.RegionalFee;
import com.example.trialtask.entity.Weather;
import com.example.trialtask.entity.WeatherFees;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeeServiceTest {
    @Autowired
    private IFeeService feeService;

    @Test
    void calculateFee_InvalidVehicleTypeException() {
        String city = "Tartu";
        String vehicleType = "train";
        String expectedMessage = "Invalid vehicle type";
        Exception exception = assertThrows(Exception.class, () -> feeService.calculateFee(city, vehicleType));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void calculateFee_weatherNotFoundException() {
        String city = "Haapsalu";
        String vehicleType = "car";
        String expectedMessage = "Could not find station for Haapsalu";
        Exception exception = assertThrows(Exception.class, () -> feeService.calculateFee(city, vehicleType));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getFeeFromWeather() {
        Weather weather = Weather.builder().station("Tartu-Tõravere").airTemp(-12.6).windSpeed(14.2).weatherPhenomenon("Light rain").build();
        double expectedFee = 2;
        assertDoesNotThrow(() -> {
            double actualFee = feeService.getFeeFromWeather(weather, true);
            assertEquals(expectedFee, actualFee);
        });
    }

    @Test
    void getFeeFromWeather_exception() {
        Weather weather = Weather.builder().station("Tartu-Tõravere").airTemp(-11.4).windSpeed(2.4).weatherPhenomenon("Thunder").build();
        String expectedMessage = "Usage of selected vehicle type is forbidden";
        Exception exception = assertThrows(Exception.class, () -> feeService.getFeeFromWeather(weather, true));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getAllRegionalFees() {
        String expectedResult = "{city=Pärnu car=3.0 scooter=2.5 bike=2.0}";
        String actualResult = feeService.getAllRegionalFees();
        assertTrue(actualResult.contains(expectedResult));
    }

    @Test
    void getRegionalFeesByCity() {
        String city = "Pärnu";
        RegionalFee expectedRegionalFee = RegionalFee.builder().city("Pärnu").carFee(3).scooterFee(2.5).bikeFee(2).build();
        assertDoesNotThrow(() -> {
            RegionalFee actualRegionalFee = feeService.getRegionalFeesByCity(city);
            assertEquals(expectedRegionalFee.toString(), actualRegionalFee.toString());
        });
    }

    @Test
    void getRegionalFeesByCity_exception() {
        String city = "Viljandi";
        String expectedMessage = "Fees for Viljandi not found";
        Exception exception = assertThrows(Exception.class, () -> feeService.getRegionalFeesByCity(city));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getRegionalFeeByCityAndVehicleType() {
        String city = "Tartu";
        String vehicleType = "scooter";
        double expectedFee = 3;
        assertDoesNotThrow(() -> {
            double actualFee = feeService.getRegionalFeeByCityAndVehicleType(city, vehicleType);
            assertEquals(expectedFee, actualFee);
        });
    }

    @Test
    void getRegionalFeeByCityAndVehicleType_exception() {
        String city = "Tartu";
        String vehicleType = "truck";
        String expectedMessage = "Invalid vehicle type";
        Exception exception = assertThrows(Exception.class, () ->
                feeService.getRegionalFeeByCityAndVehicleType(city, vehicleType));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void setRegionalFee_success() {
        String city = "Tartu";
        String vehicleType = "car";
        double fee = 3.5;
        boolean expectedResult = true;
        assertDoesNotThrow(() -> {
            boolean actualResult = feeService.setRegionalFee(city, vehicleType, fee);
            assertEquals(expectedResult, actualResult);
        });
    }

    @Test
    void setRegionalFee_failure() {
        String city = "Tartu";
        String vehicleType = "truck";
        double fee = 2;
        boolean expectedResult = false;
        assertDoesNotThrow(() -> {
            boolean actualResult = feeService.setRegionalFee(city, vehicleType, fee);
            assertEquals(expectedResult, actualResult);
        });
    }

    @Test
    void setRegionalFee_exception() {
        String city = "Viljandi";
        String vehicleType = "car";
        double fee = 1;
        String expectedMessage = "Fees for Viljandi not found";
        Exception exception = assertThrows(Exception.class, () ->
                feeService.setRegionalFee(city, vehicleType, fee));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getWeatherFees() {
        WeatherFees expectedFees = WeatherFees.builder().atef_lessThanMinusTen(1)
                .atef_minusTenToZero(0.5).wsef_tenToTwentyMps(0.5)
                .wpef_snowOrSleet(1).wpef_rain(0.5).build();
        assertDoesNotThrow(() -> {
            WeatherFees actualFees = feeService.getWeatherFees();
            assertEquals(expectedFees.toString(), actualFees.toString());
        });
    }

    @Test
    void getWeatherFee() {
        String weather = "wpef_rain";
        double expectedFee = 0.5;
        assertDoesNotThrow(() -> {
            double actualFee = feeService.getWeatherFee(weather);
            assertEquals(expectedFee, actualFee);
        });
    }

    @Test
    void getWeatherFee_exception() {
        String weather = "nothing";
        String expectedMessage = "Invalid weather parameter";
        Exception exception = assertThrows(Exception.class, () ->
                feeService.getWeatherFee(weather));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void setWeatherFee_success() {
        String weather = "wpef_rain";
        double fee = 0.5;
        boolean expectedResult = true;
        assertDoesNotThrow(() -> {
            boolean actualResult = feeService.setWeatherFee(weather, fee);
            assertEquals(expectedResult, actualResult);
        });
    }

    @Test
    void setWeatherFee_failure() {
        String weather = "nothing";
        double fee = 0.5;
        boolean expectedResult = false;
        assertDoesNotThrow(() -> {
            boolean actualResult = feeService.setWeatherFee(weather, fee);
            assertEquals(expectedResult, actualResult);
        });
    }
}