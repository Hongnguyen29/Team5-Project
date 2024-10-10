package com.example.restaurant.restaurants;

import com.example.restaurant.auth.dto.Passwordto;
import com.example.restaurant.restaurants.dto.RestaurantDto;
import com.example.restaurant.restaurants.dto.RestaurantViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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






}
