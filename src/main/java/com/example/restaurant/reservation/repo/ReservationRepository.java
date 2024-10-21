package com.example.restaurant.reservation.repo;

import com.example.restaurant.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("SELECT COUNT(r) > 0 FROM ReservationEntity r WHERE r.user.id = :userId AND r.date = :date AND r.time = :time")
    boolean existsByUserAndDateAndTime(
            @Param("userId") Long userId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time);

}
