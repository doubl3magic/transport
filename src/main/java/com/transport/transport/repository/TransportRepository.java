package com.transport.transport.repository;

import com.transport.transport.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransportRepository extends JpaRepository<Transport, Long> {

    List<Transport> findByEndLocation(String endLocation);

    List<Transport> findByPaid(boolean paid);

    @Query("""
                SELECT t
                FROM Transport t
                WHERE t.departureDate BETWEEN :start AND :end
            """)
    List<Transport> findTransportsInPeriod(LocalDate start, LocalDate end);

    @Query("""
                SELECT t.driver.name, COUNT(t), SUM(t.price)
                FROM Transport t
                WHERE t.paid = true
                GROUP BY t.driver.name
            """)
    List<Object[]> revenuePerDriver();

}
