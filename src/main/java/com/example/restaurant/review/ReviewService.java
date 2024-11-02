package com.example.restaurant.review;

import com.example.restaurant.auth.AuthenticationFacade;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.ReservationStatus;
import com.example.restaurant.reservation.dto.ReservationView;
import com.example.restaurant.reservation.entity.ReservationEntity;
import com.example.restaurant.reservation.repo.ReservationRepository;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import com.example.restaurant.review.dto.ReviewDto;
import com.example.restaurant.review.entity.ReviewEntity;
import com.example.restaurant.review.dto.ReviewViewDto;
import com.example.restaurant.review.repo.ReviewRepository;
import com.example.restaurant.support.ImageFileUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReservationRepository reservationRepo;
    private final ReviewRepository reviewRepository;
    private final AuthenticationFacade facade;
    private final ImageFileUtils fileUtils;

    @Transactional
    public ReviewViewDto create(ReviewDto dto , Long reservationId){
        UserEntity user = facade.extractUser();
        ReservationEntity reservation = reservationRepo
                .findById(reservationId).orElseThrow();
        if(!(user.getUsername().equals(reservation.getUser().getUsername()))
                || !(reservation.getStatus().equals(ReservationStatus.USED)) ){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "This operation cannot be performed.");
        }
        String path = fileUtils.saveFile(String.format(
                "review/%d/",user.getId()),
                "reservation",
                dto.getImage()
                );
        ReviewEntity review = new ReviewEntity();
        review.setContent(dto.getContent());
        review.setStar(dto.getStar());
        review.setImage(path);
        review.setTimeCreate(LocalDateTime.now());
        review.setRestaurant(reservation.getRestaurant());
        review.setUser(user);
        review.setReservation(reservation);

        reviewRepository.save(review);

        return ReviewViewDto.fromEntity(review);
    }
    @Transactional
    public ReviewViewDto update(ReviewDto dto, Long reviewId){
        UserEntity user = facade.extractUser();

        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found."));
        log.info("loi 1");
        if(!(user.getUsername().equals(review.getUser().getUsername()))){
            log.info("loi 2");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "This operation cannot be performed.");
        }
        log.info("loi 3");
        String path = fileUtils.saveFile(String.format(
                        "review/%d/",user.getId()),
                "reservation",
                dto.getImage()
        );
        log.info("loi 4");
        review.setContent(dto.getContent());
        review.setStar(dto.getStar());
        review.setImage(path);
        log.info("loi 5");

        reviewRepository.save(review);

        return ReviewViewDto.fromEntity(review);
    }
    @Transactional
    public void delete(Long reviewId){
        UserEntity user = facade.extractUser();

        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found."));
        if(!(user.getUsername().equals(review.getUser().getUsername()))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "This operation cannot be performed.");
        }
        reviewRepository.deleteById(reviewId);
    }

    public ReviewViewDto readOnd(Long reviewId){
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow();
        return ReviewViewDto.fromEntity(review);
    }
    public List<ReviewViewDto> getAllUser(){
        UserEntity  user = facade.extractUser();

        List<ReviewEntity> reviewEntities = reviewRepository
                .findByUserIdOrderByTimeCreateDesc(user.getId());

        return reviewEntities.stream()
                .map(ReviewViewDto::fromEntity)
                .toList();
    }
    public List<ReviewViewDto> getAllRestaurant(Long restId){
        List<ReviewEntity> reviewEntities = reviewRepository
                .findByRestaurantIdOrderByTimeCreateDesc(restId);
        return reviewEntities.stream()
                .map(ReviewViewDto::fromEntity)
                .toList();
    }
    public double starRestaurant(Long restId){
        double star = reviewRepository.findAverageStarByRestaurantId(restId);
        return Math.round(star * 10.0) / 10.0;
    }









}
