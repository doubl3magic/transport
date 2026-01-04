package com.transport.transport.service;

import com.transport.transport.model.Company;
import com.transport.transport.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;

    public List<Company> findAll() {
        return repository.findAll();
    }

    public Optional<Company> findById(Long id) {
        return repository.findById(id);
    }

    public void save(Company company) {
        repository.save(company);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Company> findAllSortedByName() {
        return repository.findAll(Sort.by("name"));
    }

    public List<Company> findAllSortedByRevenue() {
        return repository.findAll(Sort.by("revenue").descending());
    }
}
