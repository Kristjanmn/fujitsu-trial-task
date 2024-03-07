package com.example.trialtask.controller;

import com.example.trialtask.service.IFeeService;
import com.example.trialtask.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("manage/")
public class ManagerController {
    private final IFeeService feeService;
    private final IWeatherService weatherService;

    @GetMapping("getWeatherFee")
    ResponseEntity getWeatherFees(
            @RequestParam("weather") Optional<String> weather
    ) {
        try {
            if (weather.isPresent())
                return ResponseEntity.ok().body(feeService.getWeatherFee(weather.get()));
            return ResponseEntity.ok().body(feeService.getWeatherFees().toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("setWeatherFee")
    ResponseEntity<String> setWeatherFee(
            @RequestParam("weather") String weather,
            @RequestParam("fee") double fee
    ) {
        try {
            if (feeService.setWeatherFee(weather, fee))
                return ResponseEntity.ok().body("Weather fees updated");
            return ResponseEntity.badRequest().body("Failed to update weather fees");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("getRegionalFee")
    ResponseEntity getRegionalFee(
            @RequestParam("city") Optional<String> city,
            @RequestParam("vehicleType") Optional<String> vehicleType
    ) {
        try {
            if (city.isEmpty()) return ResponseEntity.ok().body(feeService.getAllRegionalFees());
            if (vehicleType.isEmpty()) return ResponseEntity.ok().body(feeService.getRegionalFeesByCity(city.get()).toString());
            return ResponseEntity.ok().body(feeService.getRegionalFeeByCityAndVehicleType(city.get(), vehicleType.get()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("setRegionalFee")
    ResponseEntity<String> setRegionalFee(
            @RequestParam("city") String city,
            @RequestParam("vehicleType") String vehicleType,
            @RequestParam("fee") double fee
    ) {
        try {
            if (feeService.setRegionalFee(city, vehicleType, fee))
                return ResponseEntity.ok().body("Regional fee updated");
            return ResponseEntity.badRequest().body("Failed to update regional fees");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("getWeatherStations")
    ResponseEntity<String> getWeatherStations() {
        return ResponseEntity.ok().body(weatherService.getStationListAsString());
    }

    @PostMapping("addWeatherStation")
    ResponseEntity<String> addWeatherStation(
            @RequestParam("city") String city,
            @RequestParam("station") String station
    ) {
        if (weatherService.addWeatherStation(city, station))
            return ResponseEntity.ok().body("Added weather station: "+city+" - "+station);
        return ResponseEntity.badRequest().body("Failed to add station: "+city+" - "+station);
    }

    @DeleteMapping("removeWeatherStation")
    ResponseEntity<String> removeWeatherStation(
            @RequestParam("city") String city,
            @RequestParam("station") String station
    ) {
        if (weatherService.removeWeatherStation(city, station))
            return ResponseEntity.ok().body("Removed weather station: "+city+" - "+station);
        return ResponseEntity.badRequest().body("Failed to remove station: "+city+" - "+station);
    }
}
