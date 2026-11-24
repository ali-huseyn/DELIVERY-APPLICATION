package org.example.mspaymentservice.service

import com.example.mspaymentservice.dto.OrderEvent
import com.example.mspaymentservice.entity.CourierBalanceEntity
import com.example.mspaymentservice.entity.PaymentEntity
import com.example.mspaymentservice.exception.OrderAlreadyExistsException
import com.example.mspaymentservice.mapper.CourierBalanceMapper
import com.example.mspaymentservice.mapper.PaymentMapper
import com.example.mspaymentservice.repository.CourierBalanceRepository
import com.example.mspaymentservice.repository.PaymentRepository
import com.example.mspaymentservice.service.PaymentService
import spock.lang.Specification

import static com.example.mspaymentservice.enums.PaymentStatus.COMPLETED
import static com.example.mspaymentservice.enums.PaymentStatus.PENDING
import static com.example.mspaymentservice.enums.PaymentStatus.CANCELLED

class PaymentServiceSpec extends Specification {


    private PaymentRepository paymentRepository
    private CourierBalanceRepository courierBalanceRepository
    private PaymentMapper paymentMapper
    private CourierBalanceMapper courierBalanceMapper


    private PaymentService paymentService

    def setup() {
        paymentRepository = Mock()
        courierBalanceRepository = Mock()
        paymentMapper = Mock()
        courierBalanceMapper = Mock()
        paymentService = new PaymentService(paymentRepository,
                courierBalanceRepository,
                paymentMapper,
                courierBalanceMapper)

    }

    def "should create pending payment when payment does not exist"() {

        given:
        def event = new OrderEvent()
        event.setOrderId(1L)
        event.setCourierId(10L)
        event.setCourierName("Ali")
        event.setDeliveryPrice(new BigDecimal("5.00"))
        event.setCustomerEmail("ali@gmail.com")


        def paymentEntity = new PaymentEntity()


        when:

        paymentService.createPendingPayment(event)

        then:
        1 * paymentRepository.findByOrderId(event.getOrderId()) >> Optional.empty()
        1 * paymentMapper.toEntity(event, PENDING) >> paymentEntity
        1 * paymentRepository.save(paymentEntity)


    }

    def "should throw exception when payment already exists"() {

        given:

        def event = new OrderEvent()
        event.setOrderId(1l)


        when:
        paymentService.createPendingPayment(event)

        then:
        1 * paymentRepository.findByOrderId(event.getOrderId()) >> Optional.of(new PaymentEntity())
        thrown(OrderAlreadyExistsException)
        0 * paymentMapper._
        0 * paymentRepository.save(_)

    }

    def "should throw exception when payment does not exist "() {

        given:
        def event = new OrderEvent()
        event.setOrderId(1L)


        when:
        paymentService.completePayment(event)

        then:
        1 * paymentRepository.findByOrderId(event.getOrderId()) >> Optional.empty()

        thrown(IllegalStateException)

        0 * courierBalanceRepository._
        0 * courierBalanceMapper._
    }

    def "should return when payment status is COMPLETED"() {

        given:
        def event = new OrderEvent()
        event.setOrderId(1L)
        def payment = new PaymentEntity()
        payment.setPaymentStatus(COMPLETED)
        when:
        paymentService.completePayment(event)
        then:
        1 * paymentRepository.findByOrderId(event.getOrderId()) >> Optional.of(payment)

        0 * courierBalanceRepository._
        0 * courierBalanceMapper._

    }

    def "should throw exception when courier IDs do not match"() {

        given:
        def event = new OrderEvent()
        event.setOrderId(1L)
        event.setCourierId(20L)
        def payment = new PaymentEntity()
        payment.setCourierId(10L)
        payment.setPaymentStatus(PENDING)

        when:
        paymentService.completePayment(event)

        then:
        1 * paymentRepository.findByOrderId(1L)
                >> Optional.of(payment)
           thrown(IllegalStateException)
        0 * courierBalanceRepository._
        0 * courierBalanceMapper._
    }

    def "should payment is not Pending" (){

        given:
        def event = new OrderEvent()
        event.setOrderId(1L)
        event.setCourierId(10L)

        def payment = new PaymentEntity()
        payment.setOrderId(1L)
        payment.setCourierId(10L)
        payment.setPaymentStatus(CANCELLED)

        when:

        paymentService.completePayment(event)

        then:

       1*paymentRepository.findByOrderId(1L)>>Optional.of(payment)
        thrown(IllegalStateException)

        0 * courierBalanceRepository._
        0 * courierBalanceMapper._

    }

    def "should every think okay for process"(){

        given:

        def courier = new CourierBalanceEntity()
        courier.setCourierId(10L)
        courier.setBalance(new BigDecimal("5.00"))
        courier.setTotalBalance(new BigDecimal("10.00"))
        def event=new OrderEvent()
        event.setOrderId(1L)
        event.setCourierId(10L)
        def payment = new PaymentEntity()
        payment.setCourierId(10L)
        payment.setPaymentStatus(PENDING)
        BigDecimal earningAmount = new BigDecimal("3.00");
        payment.setEarningAmount(earningAmount)

        when:
        paymentService.completePayment(event)

        then:
        1 * paymentRepository.findByOrderId(1L) >> Optional.of(payment)
        1 * courierBalanceRepository.findByCourierId(10L) >> Optional.of(courier)


        payment.getPaymentStatus() == COMPLETED
        payment.getEarningAmount() == new BigDecimal("3.00")
        payment.getCompletedAt() != null

        courier.getBalance() == new BigDecimal("8.00")
        courier.getTotalBalance() == new BigDecimal("13.00")
    }
    def "should create courier balance when courier does not exist  "(){
        given:

        def courier = new CourierBalanceEntity()
        courier.setCourierId(10L)
        courier.setBalance(BigDecimal.ZERO)
        courier.setTotalBalance(BigDecimal.ZERO)

        def event=new OrderEvent()
        event.setOrderId(1L)
        event.setCourierId(10L)
        def payment = new PaymentEntity()
        payment.setOrderId(1L)
        payment.setCourierId(10L)
        payment.setPaymentStatus(PENDING)
        BigDecimal earningAmount = new BigDecimal("3.00");
        payment.setEarningAmount(earningAmount)

        when:
        paymentService.completePayment(event)

        then:
        1*paymentRepository.findByOrderId(1L)>>Optional.of(payment)
        1*courierBalanceRepository.findByCourierId(10L)>>Optional.empty()
        1*courierBalanceMapper.toEntity(event)>>courier


        payment.getPaymentStatus() == COMPLETED
        payment.getEarningAmount() == new BigDecimal("3.00")

        courier.getBalance() == new BigDecimal("3.00")
        courier.getTotalBalance() == new BigDecimal("3.00")

    }
    


}