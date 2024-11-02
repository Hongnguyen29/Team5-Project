package com.example.restaurant.restaurants.repo;

import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.Category;
import com.example.restaurant.enumList.ReservationStatus;
import com.example.restaurant.enumList.RestStatus;
import com.example.restaurant.reservation.entity.ReservationEntity;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long>,
        JpaSpecificationExecutor<RestaurantEntity> {



    List<RestaurantEntity> findByNameRestaurantContaining(String name);

    List<RestaurantEntity> findByAddressContaining(String address);
    boolean existsById(Long id);

    boolean existsByUser_Id(Long userId);

    Optional<RestaurantEntity> findByUser(UserEntity user);

   /* @Query("SELECT DISTINCT r FROM RestaurantEntity r JOIN FETCH r.menus WHERE r.id= :id")
    Optional<RestaurantEntity> resMenus(@Param("id") Long id);*/

    List<RestaurantEntity> findByStatus(RestStatus status);

}
