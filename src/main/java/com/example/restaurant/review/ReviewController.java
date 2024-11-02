package com.example.restaurant.review;

import com.example.restaurant.review.dto.ReviewDto;
import com.example.restaurant.review.dto.ReviewViewDto;
import com.example.restaurant.review.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/user/reservation/{reservationId}")  //viet review
    public ResponseEntity<ReviewViewDto> create(
            @ModelAttribute ReviewDto dto,
            @PathVariable Long reservationId
            ){
        ReviewViewDto reviewViewDto = reviewService.create(dto,reservationId);
        return ResponseEntity.ok(reviewViewDto);
    }
    @PutMapping("/user/review/{reviewId}") //sua review
    public ResponseEntity<ReviewViewDto> update(
            @ModelAttribute ReviewDto dto,
            @PathVariable Long reviewId
    ){
        ReviewViewDto reviewViewDto = reviewService.update(dto,reviewId);
        log.info("lỗi controller");
        return ResponseEntity.ok(reviewViewDto);
    }
    @DeleteMapping("/user/review/{reviewId}")  //xoa review
    public ResponseEntity<String> delete(
            @PathVariable Long reviewId
    ){
        reviewService.delete(reviewId);
        return ResponseEntity.ok("Successfully deleted the review.");
    }
    @GetMapping("/review/{reviewId}")  // review cua the theo id
    public ResponseEntity<ReviewViewDto> getOne(
            @PathVariable Long reviewId
    ){
        ReviewViewDto reviewViewDto = reviewService.readOnd(reviewId);
        return ResponseEntity.ok(reviewViewDto);
    }
    @GetMapping("/user/review")  // toan bo review cua nguoi dung
    public ResponseEntity<List<ReviewViewDto>> getAllUser(){
        List<ReviewViewDto> reviews = reviewService.getAllUser();
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/restaurant/{restId}/review")  //toan bo review cua restaurant
    public ResponseEntity<List<ReviewViewDto>> getAllRestaurant(
            @PathVariable Long restId
    ){
        List<ReviewViewDto> reviews = reviewService.getAllRestaurant(restId);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/restaurant/{restId}/star")  // sao của nhà hàng
    public ResponseEntity<Double> starRestaurant(
            @PathVariable Long restId
    ){
        Double star  = reviewService.starRestaurant(restId);
        return ResponseEntity.ok(star);
    }


}
