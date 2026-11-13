package com.example.mspaymentservice.repository;

import com.example.mspaymentservice.entity.CourierBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourierBalanceRepository extends JpaRepository<CourierBalanceEntity, Long> {

    Optional<CourierBalanceEntity> findByCourierId(Long courierId);
}
