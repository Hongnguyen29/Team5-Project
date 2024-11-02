package com.example.restaurant.enumList;

public enum ReservationStatus {
    PENDING,       // Đang chờ xử lý
    ACCEPTED,      // Đã chấp nhận
    REJECTED,      // Đã từ chối
    CANCELLED,     // Đã hủy
    USED,          // Đã dùng
    NO_SHOW ,  // Đã không dùng (khách hàng không tới)
    SYSTEM_CANCELLED  // Trạng thái hủy do hệ thống
}
