package com.example.restaurant.menu;

import com.example.restaurant.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
