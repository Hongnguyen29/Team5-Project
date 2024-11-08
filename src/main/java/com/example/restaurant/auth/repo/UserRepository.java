package com.example.restaurant.auth.repo;

import com.example.restaurant.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.email = :email AND u.id <> :userId")
    boolean existsByEmailAndNotUserId(@Param("email") String email, @Param("userId") Long userId);
    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.phone = :phone AND u.id <> :userId")
    boolean existsByPhoneAndNotUserId(@Param("phone") String phone, @Param("userId") Long userId);
}
