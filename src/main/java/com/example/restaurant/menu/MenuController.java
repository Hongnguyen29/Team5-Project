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
            @ModelAttribute
            MenuDto dto
    ){
        try {
            MenuViewDto menuViewDto = menuService.addMenu(dto);

            log.info("!!!!!!!!-------" + menuViewDto.toString());
            return ResponseEntity.ok(menuViewDto);
        } catch (ResponseStatusException e) {
            log.info("ccccccccccccccc");
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            log.info("ddddddddddddddd");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }
    @PutMapping("/rest/menu/{menuId}")
    public ResponseEntity<?> updateMenu(
            @ModelAttribute
            MenuDto dto,
            @PathVariable
            Long menuId
    ){
        try {
            MenuViewDto menuViewDto = menuService.updateMenu(menuId,dto);
            return ResponseEntity.ok(menuViewDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }
    @DeleteMapping("/rest/menu/{menuId}")
    public ResponseEntity<String> deleteMenu(
            @PathVariable
            Long menuId
    ){
        try {
            String tap = menuService.deleteMenu(menuId);
            return ResponseEntity.ok(tap);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }
    @GetMapping("/restaurant/menu/{menuId}")
    public ResponseEntity<?> readOneMenu(
            @PathVariable
            Long menuId
    ){
        try {
            MenuViewDto viewDto = menuService.readOne(menuId);
            return ResponseEntity.ok(viewDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }
    @GetMapping("/restaurant/{restId}/menu")
    public ResponseEntity<?> readAllMenu(
            @PathVariable
            Long restId
    ){
        try {
            List<MenuViewDto> viewDto = menuService.readAll(restId);
            return ResponseEntity.ok(viewDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }
    @GetMapping("/restaurant/1/{restId}")
    public ResponseEntity<?> readAllMenu(
            Pageable pageable,
            @PathVariable
            Long restId
    ){
        try {
            Page<MenuViewDto> viewDto = menuService.readPage(pageable,restId);
            return ResponseEntity.ok(viewDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }


}
