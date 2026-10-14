package org.example.mscourierservice.repository;

import org.example.mscourierservice.entity.CourierEntity;

import org.example.mscourierservice.enums.CourierStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<CourierEntity,Long> {

    CourierEntity findFirstByStatus(CourierStatus status);
}
