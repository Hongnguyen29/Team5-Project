package com.example.restaurant.menu;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class MenuController {
    private final MenuService menuService;

}
