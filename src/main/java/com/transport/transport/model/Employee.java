package com.transport.transport.model;

import com.transport.transport.model.enums.Qualification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Positive
    private double salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Qualification qualification;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id")
    private Company company;

    // getters & setters
}
