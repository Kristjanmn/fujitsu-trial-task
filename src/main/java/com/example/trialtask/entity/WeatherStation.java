package com.example.trialtask.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "weatherStations")
public class WeatherStations {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String station;
}
