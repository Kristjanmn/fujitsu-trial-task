package com.example.trialtask.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Prefix meanings:
 * atef_ -> Air temperature extra fee
 * wsef_ -> Wind speed extra fee
 * wpef_ -> Weather phenomenon extra fee
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "weatherFees")
public class WeatherFees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private double atef_lessThanMinusTen;

    @Column(nullable = false)
    private double atef_minusTenToZero;

    @Column(nullable = false)
    private double wsef_tenToTwentyMps;

    @Column(nullable = false)
    private double wpef_snowOrSleet;

    @Column(nullable = false)
    private double wpef_rain;

    public String toString() {
        StringBuilder sb = new StringBuilder("{Air temperature less than -10=").append(getAtef_lessThanMinusTen());
        sb.append(" Air temperature -10 to 0=").append(getAtef_minusTenToZero());
        sb.append(" Wind speed 10 to 20 m/s=").append((getWsef_tenToTwentyMps()));
        sb.append(" Snow or sleet=").append(getWpef_snowOrSleet());
        sb.append(" rain=").append(getWpef_rain()).append("}");
        return sb.toString();
    }
}
