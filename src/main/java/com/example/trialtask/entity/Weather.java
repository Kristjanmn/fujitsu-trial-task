package com.example.trialtask.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String station;

    @Column(nullable = false)
    private int wmoCode;

    @Column(nullable = false)
    private double airTemp;

    @Column(nullable = false)
    private double windSpeed;

    @Column(nullable = false)
    private String weatherPhenomenon;

    @Column(nullable = false)
    private long timestamp;
}
