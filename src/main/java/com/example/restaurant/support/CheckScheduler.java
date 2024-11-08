package com.example.restaurant.support;


import com.example.restaurant.enumList.ReservationStatus;
import com.example.restaurant.enumList.RestStatus;
import com.example.restaurant.reservation.entity.ReservationEntity;
import com.example.restaurant.reservation.repo.ReservationRepository;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CheckScheduler {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Scheduled(fixedRate = 60000*30) // Chạy mỗi 30 phút (60000ms)
    public void checkPendingReservations() {
        List<ReservationEntity> pendingReservations = reservationRepository.findByStatus(ReservationStatus.PENDING);
        LocalDateTime now = LocalDateTime.now();
        for (ReservationEntity reservation : pendingReservations) {
            if (now.isAfter(reservation.getTime().minusHours(1))) { // sua lai thanh truoc 1 se tu dong chuyen thanh huy
                reservation.setStatus(ReservationStatus.SYSTEM_CANCELLED); // Đặt trạng thái thành CANCELLED
                reservation.setProcessedAt(LocalDateTime.now());
                reservationRepository.save(reservation); // Lưu thay đổi
            }
        }
        List<ReservationEntity> acceptedReservations = reservationRepository.findByStatus(ReservationStatus.ACCEPTED);
        LocalDateTime now1 = LocalDateTime.now();
        for (ReservationEntity reservation : acceptedReservations) {
            if (now1.isAfter(reservation.getTime())) {//.plusHours(5)
                reservation.setStatus(ReservationStatus.USED); // Đặt trạng thái thành USED
                reservationRepository.save(reservation); // Lưu thay đổi
            }
        }

    }
    @Scheduled(fixedRate = 60000) // Chạy mỗi phút (60000ms)
    public void checkReviewReservations() {
        List<ReservationEntity> usedReservations = reservationRepository.findByStatus(ReservationStatus.USED);
        for (ReservationEntity reservation : usedReservations) {
            if (reservation.getReview() != null) { // sua lai thanh truoc 2h se tu dong chuyen thanh huy
                reservation.setStatus(ReservationStatus.FINISH); // Đặt trạng thái thành CANCELLED
                reservationRepository.save(reservation); // Lưu thay đổi
            }
        }
    }
    @Scheduled(fixedRate = 60000*60) // Chạy mỗi gio (60000ms *60)
    public void checkCloseRestaurant() {
        List<RestaurantEntity> closeRestaurants = restaurantRepository.findByStatus(RestStatus.CLOSE);
        LocalDateTime now = LocalDateTime.now();
        for (RestaurantEntity restaurant : closeRestaurants) {
            if (now.isAfter(restaurant.getCloseRequest().getProcessedAt().plusDays(5))) {
                restaurantRepository.delete(restaurant);
            }
        }
    }




}
