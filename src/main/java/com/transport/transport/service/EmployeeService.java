package com.transport.transport.service;

import com.transport.transport.model.Employee;
import com.transport.transport.model.enums.Qualification;
import com.transport.transport.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void save(Employee employee) {
        repository.save(employee);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Employee> findSorted(String sortField) {
        if (!List.of("name", "qualification", "salary").contains(sortField)) {
            sortField = "name";
        }
        return repository.findAll(Sort.by(sortField).ascending());
    }

}
