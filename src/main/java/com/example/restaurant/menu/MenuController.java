package com.example.restaurant.menu;


import com.example.restaurant.menu.dto.MenuUpdateDto;
import com.example.restaurant.restaurants.RestaurantService;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class MenuController {
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<RestaurantEntity> getMenuForRestaurant(@PathVariable Long restaurantId) {
        RestaurantEntity findRestaurant = restaurantService.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        return ResponseEntity.ok(findRestaurant);
    }

    @PostMapping("/{restaurantId}/menu/edit")
    public String updateMenu(
            @PathVariable Long restaurantId,
            @RequestBody List<MenuUpdateDto> menuUpdateDtoList
    ) {
        RestaurantEntity findRestaurant = restaurantService.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // Gọi service để cập nhật thực đơn
        menuService.updateMenuList(findRestaurant, menuUpdateDtoList);

        return "redirect:/{restaurantId}/menu/edit"; // Điều hướng về trang chỉnh sửa sau khi cập nhật
    }

}
