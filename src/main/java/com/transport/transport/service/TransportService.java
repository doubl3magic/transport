package com.transport.transport.service;

import com.transport.transport.model.Transport;
import com.transport.transport.repository.TransportRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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

    // OLD: server-side file export (optional to keep)
    public void exportToFile() {
        try (PrintWriter writer = new PrintWriter("transports.txt")) {
            for (Transport t : repository.findAll()) {
                writer.println(t.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // NEW: download-friendly export (CSV bytes)
    public byte[] exportToBytes() {
        StringBuilder sb = new StringBuilder();

        sb.append(
                "id,startLocation,endLocation,departureDate,arrivalDate,cargoType,cargoWeight,price,paid,driverId,vehicleId,clientId\n");

        for (Transport t : repository.findAll()) {
            sb.append(t.getId()).append(',')
                    .append(escapeCsv(t.getStartLocation())).append(',')
                    .append(escapeCsv(t.getEndLocation())).append(',')
                    .append(t.getDepartureDate()).append(',')
                    .append(t.getArrivalDate()).append(',')
                    .append(t.getCargoType()).append(',')
                    .append(t.getCargoWeight() == null ? "" : t.getCargoWeight()).append(',')
                    .append(t.getPrice()).append(',')
                    .append(t.isPaid()).append(',')
                    .append(t.getDriver() != null ? t.getDriver().getId() : "").append(',')
                    .append(t.getVehicle() != null ? t.getVehicle().getId() : "").append(',')
                    .append(t.getClient() != null ? t.getClient().getId() : "")
                    .append('\n');
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String escapeCsv(String s) {
        if (s == null)
            return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    public void importFromFile() {
    }

    public List<Object[]> revenuePerDriver() {
        return repository.revenuePerDriver();
    }

    public List<Transport> getTransportsInPeriod(LocalDate start, LocalDate end) {
        return repository.findTransportsInPeriod(start, end);
    }

    public List<Transport> findAllSortedByDestination() {
        return repository.findAll(Sort.by("endLocation"));
    }

}
