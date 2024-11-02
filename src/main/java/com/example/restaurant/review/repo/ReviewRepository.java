package com.example.restaurant.review.repo;

import com.example.restaurant.review.entity.ReviewEntity;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<ReviewEntity, Long> {

    // Method to find reviews by user ID and order by timeCreate in descending order
    List<ReviewEntity> findByUserIdOrderByTimeCreateDesc(Long userId);

    // Method to find reviews by restaurant ID and order by timeCreate in descending order
    List<ReviewEntity> findByRestaurantIdOrderByTimeCreateDesc(Long restId);

    @Query("SELECT AVG(r.star) FROM ReviewEntity r WHERE r.restaurant.id = :restaurantId")
    Double findAverageStarByRestaurantId(@Param("restaurantId") Long restaurantId);
}
