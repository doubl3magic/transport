package com.transport.transport.service;

import com.transport.transport.model.Transport;
import com.transport.transport.repository.TransportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportService {

    private final TransportRepository repository;

    public List<Transport> findAll() {
        return repository.findAll();
    }

    public Transport findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transport not found"));
    }

    public void save(Transport transport) {
        if (transport.getArrivalDate().isBefore(transport.getDepartureDate())) {
            throw new IllegalArgumentException("Arrival date cannot be before departure date");
        }

        repository.save(transport);
    }

    public List<Transport> findByDestination(String end_location) {
        return repository.findByEndLocation(end_location);
    }

    public void exportToFile() {
        try (PrintWriter writer = new PrintWriter("transports.txt")) {
            for (Transport t : repository.findAll()) {
                writer.println(t.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void importFromFile() {
    }

    public List<Object[]> revenuePerDriver() {
        return repository.revenuePerDriver();
    }

    public List<Transport> getTransportsInPeriod(LocalDate start, LocalDate end) {
        return repository.findTransportsInPeriod(start, end);
    }
}
