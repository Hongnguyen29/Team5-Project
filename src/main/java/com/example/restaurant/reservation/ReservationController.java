package com.example.restaurant.reservation;

import com.example.restaurant.enumList.ReservationStatus;
import com.example.restaurant.menu.dto.MenuDto;
import com.example.restaurant.menu.dto.MenuViewDto;
import com.example.restaurant.requestOpenClose.dto.ConfirmDto;
import com.example.restaurant.reservation.dto.ReservationDto;
import com.example.restaurant.reservation.dto.ReservationView;
import com.example.restaurant.reservation.repo.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/restaurant/{restId}/reservation")
    public ResponseEntity<ReservationView> createReservation(
            @RequestBody ReservationDto dto,
            @PathVariable Long restId) {
        ReservationView reservationView = reservationService.createReservation(dto, restId);
        log.info("controller");
        return ResponseEntity.ok(reservationView);
    }
    @GetMapping("/auth/reservation/{reservationId}")
    public ResponseEntity<ReservationView> readOne(@PathVariable Long reservationId) {
        ReservationView reservationView = reservationService.readOne(reservationId);
        return ResponseEntity.ok(reservationView);
    }
    @GetMapping("/user/reservation")
    public ResponseEntity<List<ReservationView>> getAllCustomer() {
        List<ReservationView> reservations = reservationService.getAllCustomer();
        return ResponseEntity.ok(reservations); // Trả về danh sách với mã trạng thái 200
    }
    @GetMapping("/rest/reservation")
    public ResponseEntity<List<ReservationView>> getAllRestaurant(
            @RequestParam (required = false)
            ReservationStatus status) {
        List<ReservationView> reservations = reservationService.getAllRestaurant(status);
        return ResponseEntity.ok(reservations);
    }
    @PutMapping("/user/reservation/{reservationId}")
    public ResponseEntity<ReservationView> cancelled(
            @PathVariable Long reservationId) {
        ReservationView reservationView = reservationService.cancelled(reservationId);
        return ResponseEntity.ok(reservationView);
    }
    @PutMapping("/rest/reservation/{reservationId}")
    public ResponseEntity<ReservationView> confirmReservation(
            @PathVariable Long reservationId,
            @RequestBody ConfirmDto dto) {
        ReservationView reservationView = reservationService
                .confirmReservation(reservationId, dto);
        return ResponseEntity.ok(reservationView);
    }
    @PutMapping("/rest/complete/{reservationId}")
    public  ResponseEntity<ReservationView> complete(
            @PathVariable Long reservationId,
            @RequestParam ReservationStatus status
    ){
        ReservationView view = reservationService
                .completeReservation(reservationId,status);
        return ResponseEntity.ok(view);
    }


}
