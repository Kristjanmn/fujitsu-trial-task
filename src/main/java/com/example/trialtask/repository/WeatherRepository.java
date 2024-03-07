package com.example.trialtask.repository;

import com.example.trialtask.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findFirstByStationOrderByIdDesc(String station);
}
