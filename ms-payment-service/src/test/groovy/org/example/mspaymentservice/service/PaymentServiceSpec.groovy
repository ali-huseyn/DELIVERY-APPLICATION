package org.example.mspaymentservice.service

import com.example.mspaymentservice.mapper.CourierBalanceMapper
import com.example.mspaymentservice.mapper.PaymentMapper
import com.example.mspaymentservice.repository.CourierBalanceRepository
import com.example.mspaymentservice.repository.PaymentRepository
import com.example.mspaymentservice.service.PaymentService
import spock.lang.Specification
import com.example.mspaymentservice.dto.OrderEvent
import com.example.mspaymentservice.entity.PaymentEntity
import static com.example.mspaymentservice.enums.PaymentStatus.PENDING

class PaymentServiceSpec extends Specification {


    private PaymentRepository paymentRepository;
    private CourierBalanceRepository courierBalanceRepository;
    private PaymentMapper paymentMapper;
    private CourierBalanceMapper courierBalanceMapper;


    private PaymentService paymentService

    def setup() {
        paymentRepository = Mock()
        courierBalanceRepository = Mock()
        paymentMapper = Mock()
        courierBalanceMapper = Mock()
        paymentService = new PaymentService(
                paymentRepository,
                courierBalanceRepository,
                paymentMapper,
                courierBalanceMapper);

    }

    def "should create pending payment when payment does not exist"(){

        given:
        def event = new OrderEvent();
        event.setOrderId(1L)
        event.setCourierId(10L)
        event.setCourierName("Ali")
        event.setDeliveryPrice(new BigDecimal("5.00"))
        event.setCustomerEmail("ali@gmail.com")

        paymentRepository.findByOrderId(event.getOrderId())>>Optional.empty();


        def paymentEntity = new PaymentEntity()

        paymentMapper.toEntity(event, PENDING) >> paymentEntity

        when:

         paymentService.createPendingPayment(event)

        then:
        1 * paymentRepository.save(paymentEntity)


    }


}