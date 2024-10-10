package com.example.restaurant.requestOpenClose.repo;

import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.requestOpenClose.entity.OpenRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OpenRequestRepository
        extends JpaRepository<OpenRequestEntity, Long> {
    boolean existsByStatusAndUser(String status, UserEntity user);

    @Query("SELECT r FROM OpenRequestEntity r ORDER BY " +
            "CASE r.status " +
            "WHEN 'PENDING' THEN 1 " +
            "WHEN 'ACCEPTED' THEN 2 " +
            "WHEN 'REJECTED' THEN 3 " +
            "ELSE 4 END, " +
            "r.createdAt DESC")
    List<OpenRequestEntity> findAllOrderedByStatusAndCreatedAt();

    List<OpenRequestEntity> findByStatusOrderByCreatedAtDesc(String status);

    List<OpenRequestEntity> findByUserOrderByCreatedAtDesc(UserEntity user);


}
