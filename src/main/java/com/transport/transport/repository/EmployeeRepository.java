package com.transport.transport.repository;

import com.transport.transport.model.Employee;
import com.transport.transport.model.enums.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByCompanyId(Long companyId);

    List<Employee> findByQualification(Qualification qualification);
}
