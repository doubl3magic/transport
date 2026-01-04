package com.transport.transport.model;

import com.transport.transport.model.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @Positive
    @Column(nullable = false)
    private double capacity;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id")
    private Company company;
}
