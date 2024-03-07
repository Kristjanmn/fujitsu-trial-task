package com.example.trialtask.controller;

import com.example.trialtask.service.IFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FeeController {
    final private IFeeService feeService;

    @GetMapping
    public ResponseEntity getFee(
            @RequestParam("city") String city,
            @RequestParam("vehicleType") String vehicleType
    ) {
        if (city == null || city.isBlank() || vehicleType == null || vehicleType.isBlank())
            return ResponseEntity.badRequest().body("city and vehicleType parameters are required!");
        try {
            return ResponseEntity.ok().body(feeService.calculateFee(city, vehicleType));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
