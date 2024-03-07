package com.example.trialtask.repository;

import com.example.trialtask.entity.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherStationRepository extends JpaRepository<WeatherStation, Long> {
    Optional<WeatherStation> findFirstByCityEqualsIgnoreCaseOrderByIdDesc(String city);
    Optional<WeatherStation> findFirstByCityEqualsIgnoreCaseAndStationEqualsIgnoreCase(String city, String station);
}
