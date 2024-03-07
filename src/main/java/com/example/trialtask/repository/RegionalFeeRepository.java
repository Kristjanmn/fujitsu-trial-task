package com.example.trialtask.repository;

import com.example.trialtask.entity.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends JpaRepository<Fees, Integer> {

}
