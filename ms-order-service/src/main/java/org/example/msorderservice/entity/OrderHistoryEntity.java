package org.example.msorderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "order_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class OrderHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
