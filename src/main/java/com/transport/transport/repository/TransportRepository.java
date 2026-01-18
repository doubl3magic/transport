package com.transport.transport.repository;

import com.transport.transport.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Query("""
                SELECT COALESCE(SUM(t.price), 0)
                FROM Transport t
                WHERE t.paid = true
            """)
    double totalIncomePaid();

    @Query("""
                SELECT COUNT(t)
                FROM Transport t
            """)
    long totalTransports();

    @Query("""
                SELECT t.driver, COUNT(t)
                FROM Transport t
                GROUP BY t.driver
            """)
    List<Object[]> transportsCountPerDriver();

    @Query("""
                SELECT t.driver, COUNT(t), COALESCE(SUM(t.price), 0)
                FROM Transport t
                WHERE t.paid = true
                GROUP BY t.driver
            """)
    List<Object[]> incomePerDriver();

    @Query("""
                SELECT COALESCE(SUM(t.price), 0)
                FROM Transport t
                WHERE t.paid = true
                  AND t.departureDate BETWEEN :start AND :end
            """)
    double incomeForPeriod(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("""
                SELECT t.driver, COUNT(t), COALESCE(SUM(t.price), 0)
                FROM Transport t
                WHERE t.paid = true
                  AND t.departureDate BETWEEN :start AND :end
                GROUP BY t.driver
            """)
    List<Object[]> incomePerDriverForPeriod(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Modifying
    @Transactional
    @Query("UPDATE Transport t SET t.paid = true WHERE t.client.id = :clientId")
    int markAllPaidByClientId(@Param("clientId") Long clientId);

    boolean existsByClientIdAndPaidFalse(Long clientId);
}
