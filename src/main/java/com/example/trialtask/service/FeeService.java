package com.example.trialtask.service;

import com.example.trialtask.configuration.ApplicationProperties;
import com.example.trialtask.entity.RegionalFee;
import com.example.trialtask.entity.Weather;
import com.example.trialtask.entity.WeatherFees;
import com.example.trialtask.repository.RegionalFeeRepository;
import com.example.trialtask.repository.WeatherFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeeService implements IFeeService {
    private final ApplicationProperties applicationProperties;
    private final RegionalFeeRepository regionalFeeRepository;
    private final WeatherFeeRepository weatherFeeRepository;
    private final IWeatherService weatherService;

    /**
     * Calculate service fee based on location, vehicle type and weather conditions.
     *
     * @param city
     * @param vehicleType - car/scooter/bike
     * @return
     * @throws Exception
     */
    @Override
    public double calculateFee(String city, String vehicleType) throws Exception {
        Weather weather = weatherService.getLatestWeather(city);
        double fee = this.getRegionalFeeByCityAndVehicleType(city, vehicleType);
        fee += getFeeFromWeather(weather, vehicleType.equalsIgnoreCase("bike"));
        return fee;
    }

    /**
     * Calculate fee for specified weather
     *
     * @param weather
     * @param vehicleIsBike
     * @return
     * @throws Exception
     */
    @Override
    public double getFeeFromWeather(Weather weather, boolean vehicleIsBike) throws Exception {
        Optional<WeatherFees> optWeatherFees = weatherFeeRepository.findFirstByOrderByIdDesc();
        if (optWeatherFees.isEmpty()) throw new Exception("Failed to get weather data from database");
        WeatherFees weatherFees = optWeatherFees.get();
        double fee = 0;

        // forbidden weather for scooters and bikes
        for (String s : applicationProperties.getWeatherPhenomenon().getForbidden()) {
            if (weather.getWeatherPhenomenon().equalsIgnoreCase(s)) {
                throw new Exception("Usage of selected vehicle type is forbidden");
            }
        }

        // wind fee, applicable only for bike
        if (vehicleIsBike) {
            // wind speed
            if (weather.getWindSpeed() >= 10 && weather.getWindSpeed() <= 20) fee += weatherFees.getWsef_tenToTwentyMps();
            if (weather.getWindSpeed() > 20) throw new Exception("Usage of selected vehicle type is forbidden");
        }

        // air temperature
        if (weather.getAirTemp() < -10) fee += weatherFees.getAtef_lessThanMinusTen();
        if (weather.getAirTemp() >= -10 && weather.getAirTemp() <= 0) fee+= weatherFees.getAtef_minusTenToZero();

        // snow or sleet related weather
        for (String s : applicationProperties.getWeatherPhenomenon().getSnowOrSleet()) {
            if (weather.getWeatherPhenomenon().equalsIgnoreCase(s)) {
                fee += weatherFees.getWpef_snowOrSleet();
                break;
            }
        }

        // rain related weather
        for (String s : applicationProperties.getWeatherPhenomenon().getRain()) {
            if (weather.getWeatherPhenomenon().equalsIgnoreCase(s)) {
                fee += weatherFees.getWpef_rain();
                break;
            }
        }
        return fee;
    }

    /**
     * Get fees for all regions.
     * Regional fees include:
     * Car fee, Scooter fee and Bike fee
     *
     * @return
     */
    @Override
    public String getAllRegionalFees() {
        StringBuilder sb = new StringBuilder();
        for (String city : weatherService.getAllCities()) {
            try {
                sb.append(this.getRegionalFeesByCity(city).toString()).append("\n");
            } catch (Exception ignored) {}
        }
        return sb.toString();
    }

    /**
     * Get car, scooter and bike fees for specific city.
     *
     * @param city
     * @return
     * @throws Exception
     */
    @Override
    public RegionalFee getRegionalFeesByCity(String city) throws Exception {
        Optional<RegionalFee> optRegionalFees = regionalFeeRepository.findFirstByCityEqualsIgnoreCaseOrderByIdDesc(city);
        if (optRegionalFees.isEmpty()) throw new Exception("Fees for "+city+" not found");
        return optRegionalFees.get();
    }

    /**
     * Get fee for specific vehicle for specific city.
     *
     * @param city
     * @param vehicleType - car/scooter/bike
     * @return
     * @throws Exception
     */
    @Override
    public double getRegionalFeeByCityAndVehicleType(String city, String vehicleType) throws Exception {
        RegionalFee regionalFee = this.getRegionalFeesByCity(city);
        double fee;
        switch (vehicleType) {
            default:
                throw new Exception("Invalid vehicle type");
            case "car":
                fee = regionalFee.getCarFee();
                break;
            case "scooter":
                fee = regionalFee.getScooterFee();
                break;
            case "bike":
                fee = regionalFee.getBikeFee();
                break;
        }
        return fee;
    }

    /**
     * Set fee for specific vehicle for specific city.
     *
     * @param city
     * @param vehicleType - car/scooter/bike
     * @param fee
     * @return
     * @throws Exception
     */
    @Override
    public boolean setRegionalFee(String city, String vehicleType, double fee) throws Exception {
        RegionalFee regionalFee = this.getRegionalFeesByCity(city);
        switch (vehicleType) {
            default:
                return false;
            case "car":
                regionalFee.setCarFee(fee);
                break;
            case "scooter":
                regionalFee.setScooterFee(fee);
                break;
            case "bike":
                regionalFee.setBikeFee(fee);
                break;
        }
        regionalFeeRepository.save(regionalFee);
        return true;
    }

    /**
     * Get fees for weather conditions.
     *
     * @return
     * @throws Exception
     */
    @Override
    public WeatherFees getWeatherFees() throws Exception {
        Optional<WeatherFees> optWeatherFees = weatherFeeRepository.findFirstByOrderByIdDesc();
        if (optWeatherFees.isEmpty()) throw new Exception("Failed to get weather fees");
        return optWeatherFees.get();
    }

    /**
     * Get fee for specific weather condition.
     *
     * @param weather
     * @return
     * @throws Exception
     */
    @Override
    public double getWeatherFee(String weather) throws Exception {
        WeatherFees weatherFees = this.getWeatherFees();
        switch (weather) {
            default:
                throw new Exception("Invalid weather parameter");
            case "atef_lessThanMinusTen":
                return weatherFees.getAtef_lessThanMinusTen();
            case "atef_minusTenToZero":
                return weatherFees.getAtef_minusTenToZero();
            case "wsef_tenToTwentyMps":
                return weatherFees.getWsef_tenToTwentyMps();
            case "wpef_snowOrSleet":
                return weatherFees.getWpef_snowOrSleet();
            case "wpef_rain":
                return weatherFees.getWpef_rain();
        }
    }

    /**
     * Set new fee for a weather condition.
     *
     * @param weather
     * @param fee
     * @return
     * @throws Exception
     */
    @Override
    public boolean setWeatherFee(String weather, double fee) throws Exception {
        WeatherFees weatherFees = this.getWeatherFees();
        switch (weather) {
            default:
                return false;
            case "atef_lessThanMinusTen":
                weatherFees.setAtef_lessThanMinusTen(fee);
                break;
            case "atef_minusTenToZero":
                weatherFees.setAtef_minusTenToZero(fee);
                break;
            case "wsef_tenToTwentyMps":
                weatherFees.setWsef_tenToTwentyMps(fee);
                break;
            case "wpef_snowOrSleet":
                weatherFees.setWpef_snowOrSleet(fee);
                break;
            case "wpef_rain":
                weatherFees.setWpef_rain(fee);
                break;
        }
        weatherFeeRepository.save(weatherFees);
        return true;
    }
}
