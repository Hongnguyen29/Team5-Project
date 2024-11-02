package com.example.restaurant.reservation.repo;

import com.example.restaurant.enumList.ReservationStatus;
import com.example.restaurant.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("SELECT COUNT(r) > 0 FROM ReservationEntity r WHERE r.user.id = :userId AND r.time = :time")
    boolean existsByUserAndTime(
            @Param("userId") Long userId,
            @Param("time") LocalDateTime time);

    List<ReservationEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    @Query("SELECT r FROM ReservationEntity r WHERE r.restaurant.id = :restaurantId " +
            "ORDER BY CASE WHEN r.status = 'PENDING' THEN 0 ELSE 1 END, " +
            "r.createdAt DESC")
    List<ReservationEntity> findAllByRestaurantIdOrderedByPendingStatusAndTime(Long restaurantId);

    List<ReservationEntity> findByRestaurantIdAndStatusOrderByCreatedAtDesc(
            Long restaurantId, ReservationStatus status);
    List<ReservationEntity> findByStatus(ReservationStatus status);
}
