package com.example.restaurant.reservation;

import com.example.restaurant.auth.AuthenticationFacade;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.ReservationStatus;
import com.example.restaurant.requestOpenClose.dto.ConfirmDto;
import com.example.restaurant.reservation.dto.ReservationDto;
import com.example.restaurant.reservation.dto.ReservationView;
import com.example.restaurant.reservation.entity.ReservationEntity;
import com.example.restaurant.reservation.repo.ReservationRepository;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final AuthenticationFacade facade;


    @Transactional
    public ReservationView createReservation(
            ReservationDto dto,
            Long restaurantId
    ){
        LocalDateTime time = LocalDateTime.of(
                LocalDate.parse(dto.getDate().toString()), LocalTime.parse(dto.getTime().toString()));
        RestaurantEntity restaurant = restaurantRepository
                .findById(restaurantId).orElseThrow();
        log.info("service1");

        UserEntity user = facade.extractUser();
        log.info("service2");

        if(reservationRepository.existsByUserAndTime(
                user.getId(),time)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You already have an appointment at this time.");
        }
        log.info("service3");

        ReservationEntity reservation =
                ReservationEntity.builder()
                        .nameCustom(dto.getNameCustom())
                        .time(time)
                        .peopleNumber(dto.getPeopleNumber())
                        .note(dto.getNote())
                        .createdAt(LocalDateTime.now())
                        .status(ReservationStatus.PENDING)
                        .user(user)
                        .restaurant(restaurant)
                        .build();
        log.info("service4");

        reservationRepository.save(reservation);
        log.info("service5");

        return ReservationView.fromEntity(reservation);
    }

    public ReservationView readOne(Long reservationId){
        UserEntity user = facade.extractUser();
        ReservationEntity reservation =
                reservationRepository.findById(reservationId).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "This reservation does not exist."));
        if(!(user.getUsername().equals(reservation.getUser().getUsername()))
                && ( user.getRestaurant().getId() != reservation.getRestaurant().getId())){
            log.info(String.valueOf(reservation.getRestaurant().getId()));
            log.info(String.valueOf(user.getRestaurant().getId()));
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This operation cannot be performed.");
        }
        return ReservationView.fromEntity(reservation);
    }
    public List<ReservationView> getAllCustomer(){  // khách hàng xem toàn bộ lịch sử đặt bàn gồm tất cả
        UserEntity user = facade.extractUser();
        List<ReservationEntity> reservations = reservationRepository
                .findAllByUserIdOrderByCreatedAtDesc(user.getId());
        return reservations.stream()
                .map(ReservationView::fromEntity)
                .toList();
    }
    public List<ReservationView> getAllRestaurant(ReservationStatus status) {  // chủ cửa hàng xem toàn bộ danh sách đặt bàn (tất cả luôn)
        UserEntity user = facade.extractUser();
        Long restaurantId = user.getRestaurant().getId();
        List<ReservationEntity> reservations;
        if(status == null){
            reservations = reservationRepository
                    .findAllByRestaurantIdOrderedByPendingStatusAndTime(restaurantId);
        }
        else {
            reservations = reservationRepository
                    .findByRestaurantIdAndStatusOrderByCreatedAtDesc(restaurantId,status);
        }
        return reservations.stream()
                .map(ReservationView::fromEntity)
                .toList();
    }

    @Transactional
    public ReservationView cancelled(Long reservationId){
        UserEntity user = facade.extractUser();
        ReservationEntity reservation =
                reservationRepository.findById(reservationId).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "This reservation does not exist."));
        if(!(user.getUsername().equals(reservation.getUser().getUsername()))){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You are not the one who made this reservation.");
        }
        if (!(reservation.getStatus().equals(ReservationStatus.PENDING)
                || reservation.getStatus().equals(ReservationStatus.ACCEPTED))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This operation cannot be performed.");
        }
        if(reservation.getStatus().equals(ReservationStatus.PENDING)
                || LocalDateTime.now().isBefore(reservation.getTime().minusHours(2))){
            // hủy trước 2 h trước thời gian đặt chỗ
            reservation.setStatus(ReservationStatus.CANCELLED);
        }
        reservationRepository.save(reservation);
        return ReservationView.fromEntity(reservation);
    }
    @Transactional
    public ReservationView confirmReservation (Long reservationId, ConfirmDto dto){
        UserEntity user = facade.extractUser();
        ReservationEntity reservation =
                reservationRepository.findById(reservationId).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "This reservation does not exist."));
        if(user.getRestaurant().getId()!= reservation.getRestaurant().getId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!(reservation.getStatus().equals(ReservationStatus.PENDING))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This operation cannot be performed.");
        }
        if(dto.isApproved()){
            reservation.setStatus(ReservationStatus.ACCEPTED);
        }
        else {
           if(dto.getReason() == null){
               throw new ResponseStatusException(
                       HttpStatus.BAD_REQUEST,"the reason must not be empty.");
           }
           reservation.setStatus(ReservationStatus.REJECTED);
           reservation.setReasonForRefusal(dto.getReason());
           reservation.setProcessedAt(LocalDateTime.now());
        }
        reservationRepository.save(reservation);
        return ReservationView.fromEntity(reservation);
    }
    @Transactional
    public ReservationView completeReservation(
            Long reservationId, ReservationStatus status
    ){  // Status : used , not_show : la phan dat cho nay da su dung hay chua
        UserEntity user = facade.extractUser();
        ReservationEntity reservation =
                reservationRepository.findById(reservationId).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "This reservation does not exist."));
        if(user.getRestaurant().getId()!= reservation.getRestaurant().getId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!(reservation.getStatus().equals(ReservationStatus.ACCEPTED )
                && (LocalDateTime.now()).isAfter(reservation.getTime()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This operation cannot be performed.");
        }
        reservation.setStatus(status);
        reservationRepository.save(reservation);
        return ReservationView.fromEntity(reservation);
    }












}
