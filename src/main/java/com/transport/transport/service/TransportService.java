package com.transport.transport.service;

import com.transport.transport.model.Employee;
import com.transport.transport.model.Transport;
import com.transport.transport.repository.TransportRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public List<Transport> findAllSortedByDestination() {
        return repository.findAll(Sort.by("endLocation"));
    }

    public long getTotalTransports() {
        return repository.totalTransports();
    }

    public double getTotalIncomePaid() {
        return repository.totalIncomePaid();
    }

    public Map<Employee, Long> getTransportsCountPerDriver() {
        Map<Employee, Long> result = new LinkedHashMap<>();
        for (Object[] row : repository.transportsCountPerDriver()) {
            Employee driver = (Employee) row[0];
            Long count = (Long) row[1];
            result.put(driver, count);
        }
        return result;
    }

    public List<Object[]> getIncomePerDriver() {
        return repository.incomePerDriver();
    }

    public double getIncomeForPeriod(LocalDate start, LocalDate end) {
        return repository.incomeForPeriod(start, end);
    }

    public List<Object[]> getIncomePerDriverForPeriod(LocalDate start, LocalDate end) {
        return repository.incomePerDriverForPeriod(start, end);
    }

    @Transactional
    public int markPaidForClient(Long clientId) {
        return repository.markAllPaidByClientId(clientId);
    }

    @Transactional
    public void markAsPaid(Long transportId) {
        Transport t = repository.findById(transportId)
                .orElseThrow(() -> new RuntimeException("Transport not found"));

        t.setPaid(true);
        repository.save(t);

        Long clientId = t.getClient().getId();
        boolean hasUnpaid = repository.existsByClientIdAndPaidFalse(clientId);
        if (!hasUnpaid) {
            t.getClient().setPaid(true);
        }
    }
}
