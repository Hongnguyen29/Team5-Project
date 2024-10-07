package com.example.restaurant.menu;

import com.example.restaurant.menu.dto.MenuRegisterDto;
import com.example.restaurant.menu.dto.MenuUpdateDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
