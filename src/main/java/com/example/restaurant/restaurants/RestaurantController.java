package com.example.restaurant.restaurants;

import com.example.restaurant.auth.dto.Passwordto;
import com.example.restaurant.enumList.Category;
import com.example.restaurant.restaurants.dto.RestaurantDto;
import com.example.restaurant.restaurants.dto.RestaurantViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restService;


    @PutMapping("/rest/updateInfo")
    public ResponseEntity<?> updateInfo(
            @RequestBody
            RestaurantDto dto
    ){
        try {
             RestaurantViewDto viewDto = restService.updateInfo(dto);
            return ResponseEntity.ok( viewDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }
    @PutMapping("/rest/updateImg")
    public ResponseEntity<?> updateInfo(
            @RequestParam
            MultipartFile file
    ){
        try {
            RestaurantViewDto dto = restService.updateImage(file);
            return ResponseEntity.ok(dto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantViewDto>> searchRestaurants(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Category category) {
        List<RestaurantViewDto> restaurants = restService.searchRestaurants(name, address, category);
        return ResponseEntity.ok(restaurants);
    }
    @GetMapping("/restaurant/{restId}")
    public ResponseEntity<?> viewRestaurant(
            @PathVariable
            Long restId
    ){
        try {
            RestaurantViewDto dto = restService.viewRestaurant(restId);
            return ResponseEntity.ok(dto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
    @GetMapping("/myRestaurant")
    public ResponseEntity<?> myRestaurant(){
        try {
            RestaurantViewDto dto = restService.myRestaurant();
            return ResponseEntity.ok(dto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }







}
