package com.example.trialtask.repository;

import com.example.trialtask.entity.WeatherFees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherFeeRepository extends JpaRepository<WeatherFees, Long> {
    Optional<WeatherFees> findFirstByOrderByIdDesc();
}
