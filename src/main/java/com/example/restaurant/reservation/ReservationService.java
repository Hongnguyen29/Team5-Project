package com.example.restaurant.reservation;

import com.example.restaurant.auth.AuthenticationFacade;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.ReservationStatus;
import com.example.restaurant.reservation.dto.ReservationDto;
import com.example.restaurant.reservation.dto.ReservationView;
import com.example.restaurant.reservation.entity.ReservationEntity;
import com.example.restaurant.reservation.repo.ReservationRepository;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

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
        RestaurantEntity restaurant = restaurantRepository
                .findById(restaurantId).orElseThrow();
        UserEntity user = facade.extractUser();

        if(reservationRepository.existsByUserAndDateAndTime(
                user.getId(),dto.getDate(),dto.getTime())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You already have an appointment at this time.");
        }

        ReservationEntity reservation =
                ReservationEntity.builder()
                        .nameCustom(dto.getNameCustom())
                        .date(dto.getDate())
                        .time(dto.getTime())
                        .peopleNumber(dto.getPeopleNumber())
                        .note(dto.getNote())
                        .createdAt(LocalDateTime.now())
                        .status(ReservationStatus.PENDING)
                        .user(user)
                        .restaurant(restaurant)

                        .build();

        reservationRepository.save(reservation);

        return ReservationView.fromEntity(reservation);
    }









}
