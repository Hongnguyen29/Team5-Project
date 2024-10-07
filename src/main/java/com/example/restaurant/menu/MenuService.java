package com.example.restaurant.menu;
import com.example.restaurant.menu.dto.MenuDto;
import com.example.restaurant.menu.dto.MenuRegisterDto;
import com.example.restaurant.menu.dto.MenuUpdateDto;
import com.example.restaurant.restaurant.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository; // Khai báo MenuRepository


}

