package com.example.restaurant.menu;

import com.example.restaurant.MenuEntity;
import com.example.restaurant.menu.dto.MenuUpdateDto;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public void updateMenuList(RestaurantEntity findRestaurant, List<MenuUpdateDto> menuUpdateDtoList) {
        List<MenuEntity> menuList = findRestaurant.getMenuList();
        for (MenuEntity menu : menuList) {
            menuRepository.delete(menu);
        }

        for (MenuUpdateDto menuUpdate : menuUpdateDtoList) {
            if (!menuUpdate.getNameFood().isEmpty()) {
                MenuEntity menu = menuUpdate.toEntity();
                menu.setRestaurant(findRestaurant);
                menuRepository.save(menu);
            }
        }

    }
}
