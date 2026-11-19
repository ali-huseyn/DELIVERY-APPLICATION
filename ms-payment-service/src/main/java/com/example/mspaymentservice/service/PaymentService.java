package com.example.mspaymentservice.service;

import com.example.mspaymentservice.dto.OrderEvent;
import com.example.mspaymentservice.exception.OrderAlreadyExistsException;
import com.example.mspaymentservice.mapper.CourierBalanceMapper;
import com.example.mspaymentservice.mapper.PaymentMapper;
import com.example.mspaymentservice.repository.CourierBalanceRepository;
import com.example.mspaymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.example.mspaymentservice.enums.PaymentStatus.COMPLETED;
import static com.example.mspaymentservice.enums.PaymentStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CourierBalanceRepository courierBalanceRepository;
    private final PaymentMapper paymentMapper;
    private final CourierBalanceMapper courierBalanceMapper;

    public void createPendingPayment(OrderEvent event) {


        if (paymentRepository.findByOrderId(event.getOrderId()).isPresent()) {
            throw new OrderAlreadyExistsException("Order already exists");
        }

        var paymentEntity = paymentMapper.toEntity(event, PENDING);

        log.info("Mapped paymentEntity: orderId={}, courierId={}, deliveryPrice={}", paymentEntity.getOrderId(), paymentEntity.getCourierId(), paymentEntity.getDeliveryPrice());


        paymentRepository.save(paymentEntity);

    }

    @Transactional
    public void completePayment(OrderEvent event) {

        var payment = paymentRepository.findByOrderId(event.getOrderId()).orElseThrow(() -> new IllegalStateException("Payment not found for orderId: " + event.getOrderId()));

        if (payment.getPaymentStatus().equals(COMPLETED)) {
            return;
        }
        if (!payment.getCourierId().equals(event.getCourierId())) {
            throw new IllegalStateException("Courier ID does not match");
        }

        if (!payment.getPaymentStatus().equals(PENDING)) {

            throw new IllegalStateException("Payment status is not PENDING");
        }

        BigDecimal earningAmount = new BigDecimal("3.00");
        payment.setEarningAmount(earningAmount);


        var courier = courierBalanceRepository.findByCourierId(event.getCourierId()).orElseGet(() -> {

            return courierBalanceMapper.toEntity(event);

        });

        courier.setBalance(courier.getBalance().add(earningAmount));
        courier.setTotalBalance(courier.getTotalBalance().add(earningAmount));

        payment.setPaymentStatus(COMPLETED);
        payment.setCompletedAt(LocalDateTime.now());


    }

}
