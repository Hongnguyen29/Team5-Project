package com.example.restaurant.requestOpenClose.repo;

import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.RequestStatus;
import com.example.restaurant.requestOpenClose.entity.CloseRequestEntity;
import com.example.restaurant.requestOpenClose.entity.OpenRequestEntity;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CloseRequestRepository extends JpaRepository<CloseRequestEntity,Long> {
    @Query("SELECT r FROM CloseRequestEntity r ORDER BY " +
            "CASE r.status " +
            "WHEN RequestStatus.PENDING THEN 1 " +
            "WHEN RequestStatus.ACCEPTED THEN 2 " +
            "ELSE 3 END, " +
            "r.createdAt DESC")
    List<CloseRequestEntity> findAllOrderedByStatusAndCreatedAt();

    List<CloseRequestEntity> findByStatusOrderByCreatedAtDesc(RequestStatus status);

    List<CloseRequestEntity> findByRestaurantOrderByCreatedAtDesc(RestaurantEntity restaurant);

    boolean existsByRestaurant(RestaurantEntity restaurant);

}
