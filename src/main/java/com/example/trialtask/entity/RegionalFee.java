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
@Table(name = "regionalBaseFee")
public class RegionalFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private double carFee;

    @Column(nullable = false)
    private double scooterFee;

    @Column(nullable = false)
    private double bikeFee;

    public String toString() {
        StringBuilder sb = new StringBuilder("{city=").append(getCity());
        sb.append(" car=").append(getCarFee());
        sb.append(" scooter=").append(getScooterFee());
        sb.append(" bike=").append(getBikeFee()).append("}");
        return sb.toString();
    }
}
