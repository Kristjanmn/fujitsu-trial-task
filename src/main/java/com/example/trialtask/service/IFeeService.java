package com.example.trialtask.service;

import com.example.trialtask.entity.RegionalFee;
import com.example.trialtask.entity.Weather;
import com.example.trialtask.entity.WeatherFees;

public interface IFeeService {
    double calculateFee(String city, String vehicleType) throws Exception;
    double getFeeFromWeather(Weather weather, boolean vehicleIsBike) throws Exception;
    String getAllRegionalFees();
    RegionalFee getRegionalFeesByCity(String city) throws Exception;
    double getRegionalFeeByCityAndVehicleType(String city, String vehicleType) throws Exception;
    boolean setRegionalFee(String city, String vehicleType, double fee) throws Exception;
    WeatherFees getWeatherFees() throws Exception;
    double getWeatherFee(String weather) throws Exception;
    boolean setWeatherFee(String weather, double fee) throws Exception;
}
