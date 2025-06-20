import com.example.paymentservice.dto.response.GetPayment;
import com.example.paymentservice.dto.response.ListPayments;
import com.example.paymentservice.exception.PaginationException;
import com.example.paymentservice.kafka.KafkaSenderPayment;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.StatusPayment;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaymentServiceImplTest {
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private KafkaSenderPayment kafkaSenderPayment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCars_NoStatus_FilterAll() {
        // arrange
        UUID paymentId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID bookingId1 = UUID.randomUUID();

        Payment payment1 = new Payment();
        payment1.setId(paymentId1);
        payment1.setUserId(userId1);
        payment1.setBookingId(bookingId1);
        payment1.setStatusPayment(StatusPayment.NEW_PAYMENT);

        UUID paymentId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID bookingId2 = UUID.randomUUID();

        Payment payment2 = new Payment();
        payment2.setId(paymentId2);
        payment2.setUserId(userId2);
        payment2.setBookingId(bookingId2);
        payment2.setStatusPayment(StatusPayment.NEW_PAYMENT);

        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment1, payment2));

        // act
        ListPayments result = paymentService.getAllPayments(null, 10L, 1L);

        // assert
        assertNotNull(result);
        List<GetPayment> cars = result.payments();

        assertNotNull(cars);
        assertEquals(2, cars.size());

        GetPayment getPayment1 = cars.getFirst();
        assertEquals(bookingId1, getPayment1.bookingId());
        assertEquals(StatusPayment.NEW_PAYMENT, getPayment1.statusPayment());
        assertEquals(userId1, getPayment1.userId());
        assertEquals(paymentId1, getPayment1.id());

        GetPayment getPayment2 = cars.getLast();
        assertEquals(bookingId2, getPayment2.bookingId());
        assertEquals(StatusPayment.NEW_PAYMENT, getPayment2.statusPayment());
        assertEquals(userId2, getPayment2.userId());
        assertEquals(paymentId2, getPayment2.id());

        verify(paymentRepository).findAll();
    }

    @Test
    void testGetCars_WithStatus_FilterByStatus() {
        // arrange
        UUID paymentId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID bookingId1 = UUID.randomUUID();

        Payment payment1 = new Payment();
        payment1.setId(paymentId1);
        payment1.setUserId(userId1);
        payment1.setBookingId(bookingId1);
        payment1.setStatusPayment(StatusPayment.NEW_PAYMENT);

        UUID paymentId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID bookingId2 = UUID.randomUUID();

        Payment payment2 = new Payment();
        payment2.setId(paymentId2);
        payment2.setUserId(userId2);
        payment2.setBookingId(bookingId2);
        payment2.setStatusPayment(StatusPayment.PAID);

        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment1, payment2));

        // act
        ListPayments result = paymentService.getAllPayments(StatusPayment.NEW_PAYMENT, 10L, 1L);

        // assert
        assertNotNull(result);
        List<GetPayment> cars = result.payments();

        assertNotNull(cars);
        assertEquals(1, cars.size());

        GetPayment getPayment1 = cars.getFirst();
        assertEquals(bookingId1, getPayment1.bookingId());
        assertEquals(StatusPayment.NEW_PAYMENT, getPayment1.statusPayment());
        assertEquals(userId1, getPayment1.userId());
        assertEquals(paymentId1, getPayment1.id());

        verify(paymentRepository).findAll();
    }

    @Test
    void testGetCars_Pagination_FirstPage() {
        // arrange
        UUID paymentId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID bookingId1 = UUID.randomUUID();

        Payment payment1 = new Payment();
        payment1.setId(paymentId1);
        payment1.setUserId(userId1);
        payment1.setBookingId(bookingId1);
        payment1.setStatusPayment(StatusPayment.NEW_PAYMENT);

        UUID paymentId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID bookingId2 = UUID.randomUUID();

        Payment payment2 = new Payment();
        payment2.setId(paymentId2);
        payment2.setUserId(userId2);
        payment2.setBookingId(bookingId2);
        payment2.setStatusPayment(StatusPayment.NEW_PAYMENT);

        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment1, payment2));

        // act
        ListPayments result = paymentService.getAllPayments(null, 1L, 1L);

        // assert
        assertNotNull(result);
        List<GetPayment> cars = result.payments();

        assertNotNull(cars);
        assertEquals(1, cars.size());

        GetPayment getPayment1 = cars.getFirst();
        assertEquals(bookingId1, getPayment1.bookingId());
        assertEquals(StatusPayment.NEW_PAYMENT, getPayment1.statusPayment());
        assertEquals(userId1, getPayment1.userId());
        assertEquals(paymentId1, getPayment1.id());

        verify(paymentRepository).findAll();
    }

    @Test
    void testGetCars_Pagination_SecondPage() {
        // arrange
        UUID paymentId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID bookingId1 = UUID.randomUUID();

        Payment payment1 = new Payment();
        payment1.setId(paymentId1);
        payment1.setUserId(userId1);
        payment1.setBookingId(bookingId1);
        payment1.setStatusPayment(StatusPayment.NEW_PAYMENT);

        UUID paymentId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID bookingId2 = UUID.randomUUID();

        Payment payment2 = new Payment();
        payment2.setId(paymentId2);
        payment2.setUserId(userId2);
        payment2.setBookingId(bookingId2);
        payment2.setStatusPayment(StatusPayment.NEW_PAYMENT);

        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment1, payment2));

        // act
        ListPayments result = paymentService.getAllPayments(null, 1L, 2L);

        // assert
        assertNotNull(result);
        List<GetPayment> cars = result.payments();

        assertNotNull(cars);
        assertEquals(1, cars.size());

        GetPayment getPayment2 = cars.getLast();
        assertEquals(bookingId2, getPayment2.bookingId());
        assertEquals(StatusPayment.NEW_PAYMENT, getPayment2.statusPayment());
        assertEquals(userId2, getPayment2.userId());
        assertEquals(paymentId2, getPayment2.id());

        verify(paymentRepository).findAll();
    }

    @Test
    void testGetCars_RequestNonexistentPage_ShouldThrow() {
        // arrange
        UUID paymentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID bookingId = UUID.randomUUID();

        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setUserId(userId);
        payment.setBookingId(bookingId);
        payment.setStatusPayment(StatusPayment.NEW_PAYMENT);
        when(paymentRepository.findAll()).thenReturn(Collections.singletonList(payment));

        // act & assert
        assertThrows(PaginationException.class, () -> {
            paymentService.getAllPayments(StatusPayment.NEW_PAYMENT, 1L, 2L);
        });
    }

}
