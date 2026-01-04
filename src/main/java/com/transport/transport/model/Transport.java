package com.transport.transport.model;

import com.transport.transport.model.enums.CargoType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "transports")
@Getter
@Setter
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String startLocation;

    @NotBlank
    private String endLocation;

    @Column(nullable = false)
    private LocalDate departureDate;

    @Column(nullable = false)
    private LocalDate arrivalDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoType cargoType;

    private Double cargoWeight;

    @Positive
    @Column(nullable = false)
    private double price;

    private boolean paid = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id")
    private Employee driver;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;
}
