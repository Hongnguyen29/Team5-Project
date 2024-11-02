package com.example.restaurant.menu;

import com.example.restaurant.auth.dto.Passwordto;
import com.example.restaurant.menu.dto.MenuDto;
import com.example.restaurant.menu.dto.MenuViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;


    @PostMapping("/rest/menu")
    public ResponseEntity<?> addMenu(
            @ModelAttribute MenuDto dto
    ) {
        MenuViewDto menuViewDto = menuService.addMenu(dto);
        return ResponseEntity.ok(menuViewDto);
    }

    @PutMapping("/rest/menu/{menuId}")
    public ResponseEntity<?> updateMenu(
            @ModelAttribute MenuDto dto,
            @PathVariable Long menuId
    ) {
        MenuViewDto menuViewDto = menuService.updateMenu(menuId, dto);
        return ResponseEntity.ok(menuViewDto);
    }

    @DeleteMapping("/rest/menu/{menuId}")
    public ResponseEntity<String> deleteMenu(
            @PathVariable Long menuId
    ) {
        String tap = menuService.deleteMenu(menuId);
        return ResponseEntity.ok(tap);
    }

    @GetMapping("/restaurant/menu/{menuId}")
    public ResponseEntity<?> readOneMenu(
            @PathVariable Long menuId
    ) {
        MenuViewDto viewDto = menuService.readOne(menuId);
        return ResponseEntity.ok(viewDto);
    }

    @GetMapping("/restaurant/{restId}/menu")  // xem danh sách menu của nhà hàng nào đó ( id) mà không chia trang
    public ResponseEntity<?> readAllMenu(
            @PathVariable Long restId
    ) {
        List<MenuViewDto> viewDto = menuService.readAll(restId);
        return ResponseEntity.ok(viewDto);
    }

    @GetMapping("/restaurant/page/{restId}")   //xem danh sách menu của nhà hàng nào đó nhung chia trang
    public ResponseEntity<?> readAllMenu(
            Pageable pageable,
            @PathVariable Long restId
    ) {
        Page<MenuViewDto> viewDto = menuService.readPage(pageable, restId);
        return ResponseEntity.ok(viewDto);
    }

}
