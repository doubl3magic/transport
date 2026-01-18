package com.transport.transport.service;

import com.transport.transport.model.Vehicle;
import com.transport.transport.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;

    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    public Vehicle findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public void save(Vehicle vehicle) {
        String reg = vehicle.getRegistrationNumber();

        if (vehicle.getId() == null) {
            // CREATE
            if (repository.existsByRegistrationNumber(reg)) {
                throw new IllegalArgumentException("Vehicle with this registration already exists");
            }
        } else {
            // UPDATE
            if (repository.existsByRegistrationNumberAndIdNot(reg, vehicle.getId())) {
                throw new IllegalArgumentException("Vehicle with this registration already exists");
            }
        }

        repository.save(vehicle);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
