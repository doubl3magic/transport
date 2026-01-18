package com.transport.transport.repository;

import com.transport.transport.model.Vehicle;
import com.transport.transport.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByCompanyId(Long companyId);

    List<Vehicle> findByType(VehicleType type);

    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);

    boolean existsByRegistrationNumberAndIdNot(String registrationNumber, Long id);

    boolean existsByRegistrationNumber(String registrationNumber);

}
