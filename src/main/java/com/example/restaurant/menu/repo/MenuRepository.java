package com.example.restaurant.menu.repo;

import com.example.restaurant.menu.entity.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity,Long> {
    boolean existsById(Long menuId);
    Page<MenuEntity> findAllByRestaurantId(Long restaurantId, Pageable pageable);
    List<MenuEntity> findAllByRestaurantId(Long restaurantId);
}
