package com.example.mspaymentservice.service;

import com.example.mspaymentservice.dto.OrderEvent;
import com.example.mspaymentservice.entity.CourierBalanceEntity;
import com.example.mspaymentservice.entity.PaymentEntity;
import com.example.mspaymentservice.exception.OrderAlreadyExistsException;
import com.example.mspaymentservice.repository.CourierBalanceRepository;
import com.example.mspaymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.example.mspaymentservice.enums.PaymentStatus.COMPLETED;
import static com.example.mspaymentservice.enums.PaymentStatus.PENDING;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CourierBalanceRepository courierBalanceRepository;

    public void createPendingPayment(OrderEvent event) {


        if (paymentRepository.findByOrderId(event.getOrderId()).isPresent()) {
            throw new OrderAlreadyExistsException("Order already exists");
        }
        var paymentEntity = new PaymentEntity();
        paymentEntity.setOrderId(event.getOrderId());
        paymentEntity.setCourierId(event.getCourierId());
        paymentEntity.setDeliveryPrice(event.getDeliveryPrice());
        paymentEntity.setPaymentStatus(PENDING);
        paymentEntity.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(paymentEntity);

    }

    @Transactional
    public void completePayment(OrderEvent event) {

        var payment = paymentRepository.findByOrderId(event.getOrderId())
                .orElseThrow(() -> new IllegalStateException(
                        "Payment not found for orderId: " + event.getOrderId()
                ));

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
        var courier = courierBalanceRepository.findByCourierId(event.getCourierId())
                .orElseGet(() -> {
                    var newBalance = new CourierBalanceEntity();
                    newBalance.setCourierId(event.getCourierId());
                    newBalance.setBalance(BigDecimal.ZERO);
                    newBalance.setTotalBalance(BigDecimal.ZERO);
                    return newBalance;
                });

        courier.setBalance(courier.getBalance().add(earningAmount));
        courier.setTotalBalance(courier.getTotalBalance().add(earningAmount));

        payment.setPaymentStatus(COMPLETED);
        payment.setCompletedAt(LocalDateTime.now());
        paymentRepository.save(payment);
        courierBalanceRepository.save(courier);


    }

}
