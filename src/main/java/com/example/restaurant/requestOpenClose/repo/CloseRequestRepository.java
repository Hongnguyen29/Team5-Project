package com.example.restaurant.requestOpenClose.repo;

import com.example.restaurant.requestOpenClose.entity.CloseRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloseRequestRepository extends JpaRepository<CloseRequestEntity,Long> {
}
