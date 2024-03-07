package com.example.trialtask.repository;

import com.example.trialtask.entity.RegionalFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionalFeeRepository extends JpaRepository<RegionalFee, Long> {
    Optional<RegionalFee> findFirstByCityEqualsIgnoreCaseOrderByIdDesc(String city);
}
