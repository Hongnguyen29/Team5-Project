package com.example.restaurant.requestOpenClose.repo;

import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.RequestStatus;
import com.example.restaurant.requestOpenClose.entity.OpenRequestEntity;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OpenRequestRepository
        extends JpaRepository<OpenRequestEntity, Long> {
    boolean existsByStatusAndUser(RequestStatus status,UserEntity user);

    @Query("SELECT r FROM OpenRequestEntity r ORDER BY " +
            "CASE r.status " +
            "WHEN RequestStatus.PENDING THEN 1 " +
            "WHEN RequestStatus.ACCEPTED THEN 2 " +
            "WHEN RequestStatus.REJECTED THEN 3 " +
            "ELSE 4 END, " +
            "r.createdAt DESC")

    List<OpenRequestEntity> findAllOrderedByStatusAndCreatedAt();

    List<OpenRequestEntity> findByStatusOrderByCreatedAtDesc(RequestStatus status);

    List<OpenRequestEntity> findByUserOrderByCreatedAtDesc(UserEntity user);

}
