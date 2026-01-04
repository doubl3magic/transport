package com.transport.transport.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String phone;
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id")
    private Company company;

    private boolean paid;
}
