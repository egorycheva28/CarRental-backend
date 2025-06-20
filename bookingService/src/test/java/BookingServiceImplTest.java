import com.example.bookingservice.dto.CarDto;
import com.example.bookingservice.dto.Status;
import com.example.bookingservice.dto.response.AvailabilityBookingResponse;
import com.example.bookingservice.dto.response.GetBooking;
import com.example.bookingservice.dto.response.ListBookings;
import com.example.bookingservice.exception.PaginationException;
import com.example.bookingservice.kafka.KafkaSenderBooking;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.StatusBooking;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.service.impl.BookingServiceImpl;
import com.example.bookingservice.service.impl.CarServiceFake;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private KafkaSenderBooking kafkaSenderBooking;

    @Mock
    private CarServiceFake carServiceFake;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void checkAvailability_statusIsFree() {
        //Arrange
        UUID carId = UUID.randomUUID();
        CarDto carDto = new CarDto(Status.FREE);

        when(carServiceFake.getCarById(carId)).thenReturn(carDto);

        // Act & Assert
        AvailabilityBookingResponse response = bookingService.checkAvailability(carId);

        assertTrue(response.availability());
    }

    @Test
    public void checkAvailability_statusIsNotFree() {
        //Arrange
        UUID carId = UUID.randomUUID();
        CarDto carDto = new CarDto(Status.BOOKED);

        when(carServiceFake.getCarById(carId)).thenReturn(carDto);

        // Act & Assert
        AvailabilityBookingResponse response = bookingService.checkAvailability(carId);

        assertFalse(response.availability());
    }

    @Test
    public void completeBooking_bookingIsNotRented() {
        //Arrange
        UUID bookingId = UUID.randomUUID();
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setStatusBooking(StatusBooking.NEW);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.completeBooking(bookingId));
    }

    @Test
    public void getBookingHistory_NoFilter() {
        //Arrange
        UUID bookingId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID carId1 = UUID.randomUUID();
        UUID paymentId1 = UUID.randomUUID();

        Booking booking1 = new Booking();
        booking1.setId(bookingId1);
        booking1.setUserId(userId1);
        booking1.setStatusBooking(StatusBooking.NEW);
        booking1.setCarId(carId1);
        booking1.setPaymentId(paymentId1);

        UUID bookingId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID carId2 = UUID.randomUUID();
        UUID paymentId2 = UUID.randomUUID();

        Booking booking2 = new Booking();
        booking2.setId(bookingId2);
        booking2.setUserId(userId2);
        booking2.setStatusBooking(StatusBooking.NEW);
        booking2.setCarId(carId2);
        booking2.setPaymentId(paymentId2);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        // act
        ListBookings result = bookingService.getBookingHistory(null, null, 5L, 1L);

        // assert
        assertNotNull(result);
        List<GetBooking> bookings = result.bookings();

        assertNotNull(bookings);
        assertEquals(2, bookings.size());

        GetBooking getBooking1 = bookings.getFirst();
        assertEquals(bookingId1, getBooking1.id());
        assertEquals(StatusBooking.NEW, getBooking1.statusBooking());
        assertEquals(userId1, getBooking1.userId());
        assertEquals(carId1, getBooking1.carId());
        assertEquals(paymentId1, getBooking1.paymentId());

        GetBooking getBooking2 = bookings.getLast();
        assertEquals(bookingId2, getBooking2.id());
        assertEquals(StatusBooking.NEW, getBooking2.statusBooking());
        assertEquals(userId2, getBooking2.userId());
        assertEquals(carId2, getBooking2.carId());
        assertEquals(paymentId2, getBooking2.paymentId());

        verify(bookingRepository).findAll();
    }

    @Test
    public void getBookingHistory_FilterByUserId() {
        //Arrange
        UUID bookingId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID carId1 = UUID.randomUUID();
        UUID paymentId1 = UUID.randomUUID();

        Booking booking1 = new Booking();
        booking1.setId(bookingId1);
        booking1.setUserId(userId1);
        booking1.setStatusBooking(StatusBooking.NEW);
        booking1.setCarId(carId1);
        booking1.setPaymentId(paymentId1);

        UUID bookingId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID carId2 = UUID.randomUUID();
        UUID paymentId2 = UUID.randomUUID();

        Booking booking2 = new Booking();
        booking2.setId(bookingId2);
        booking2.setUserId(userId2);
        booking2.setStatusBooking(StatusBooking.BOOKED);
        booking2.setCarId(carId2);
        booking2.setPaymentId(paymentId2);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        // act
        ListBookings result = bookingService.getBookingHistory(userId1, null, 5L, 1L);

        // assert
        assertNotNull(result);
        List<GetBooking> bookings = result.bookings();

        assertNotNull(bookings);
        assertEquals(1, bookings.size());

        GetBooking getBooking = bookings.getFirst();
        assertEquals(bookingId1, getBooking.id());
        assertEquals(StatusBooking.NEW, getBooking.statusBooking());
        assertEquals(userId1, getBooking.userId());
        assertEquals(carId1, getBooking.carId());
        assertEquals(paymentId1, getBooking.paymentId());

        verify(bookingRepository).findAll();
    }

    @Test
    public void getBookingHistory_FilterByCarId() {
        //Arrange
        UUID bookingId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID carId1 = UUID.randomUUID();
        UUID paymentId1 = UUID.randomUUID();

        Booking booking1 = new Booking();
        booking1.setId(bookingId1);
        booking1.setUserId(userId1);
        booking1.setStatusBooking(StatusBooking.NEW);
        booking1.setCarId(carId1);
        booking1.setPaymentId(paymentId1);

        UUID bookingId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID carId2 = UUID.randomUUID();
        UUID paymentId2 = UUID.randomUUID();

        Booking booking2 = new Booking();
        booking2.setId(bookingId2);
        booking2.setUserId(userId2);
        booking2.setStatusBooking(StatusBooking.BOOKED);
        booking2.setCarId(carId2);
        booking2.setPaymentId(paymentId2);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        // act
        ListBookings result = bookingService.getBookingHistory(null, carId1, 5L, 1L);

        // assert
        assertNotNull(result);
        List<GetBooking> bookings = result.bookings();

        assertNotNull(bookings);
        assertEquals(1, bookings.size());

        GetBooking getBooking = bookings.getFirst();
        assertEquals(bookingId1, getBooking.id());
        assertEquals(StatusBooking.NEW, getBooking.statusBooking());
        assertEquals(userId1, getBooking.userId());
        assertEquals(carId1, getBooking.carId());
        assertEquals(paymentId1, getBooking.paymentId());

        verify(bookingRepository).findAll();
    }

    @Test
    public void getBookingHistory_BothFilter() {
        //Arrange
        UUID bookingId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID carId1 = UUID.randomUUID();
        UUID paymentId1 = UUID.randomUUID();

        Booking booking1 = new Booking();
        booking1.setId(bookingId1);
        booking1.setUserId(userId1);
        booking1.setStatusBooking(StatusBooking.NEW);
        booking1.setCarId(carId1);
        booking1.setPaymentId(paymentId1);

        UUID bookingId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID carId2 = UUID.randomUUID();
        UUID paymentId2 = UUID.randomUUID();

        Booking booking2 = new Booking();
        booking2.setId(bookingId2);
        booking2.setUserId(userId2);
        booking2.setStatusBooking(StatusBooking.BOOKED);
        booking2.setCarId(carId2);
        booking2.setPaymentId(paymentId2);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        // act
        ListBookings result = bookingService.getBookingHistory(userId2, carId1, 5L, 1L);

        // assert
        assertNotNull(result);
        List<GetBooking> bookings = result.bookings();

        assertNotNull(bookings);
        assertEquals(0, bookings.size());

        verify(bookingRepository).findAll();
    }

    @Test
    public void getBookingHistory_Pagination_FirstPage() {
        //Arrange
        UUID bookingId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID carId1 = UUID.randomUUID();
        UUID paymentId1 = UUID.randomUUID();

        Booking booking1 = new Booking();
        booking1.setId(bookingId1);
        booking1.setUserId(userId1);
        booking1.setStatusBooking(StatusBooking.NEW);
        booking1.setCarId(carId1);
        booking1.setPaymentId(paymentId1);

        UUID bookingId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID carId2 = UUID.randomUUID();
        UUID paymentId2 = UUID.randomUUID();

        Booking booking2 = new Booking();
        booking2.setId(bookingId2);
        booking2.setUserId(userId2);
        booking2.setStatusBooking(StatusBooking.BOOKED);
        booking2.setCarId(carId2);
        booking2.setPaymentId(paymentId2);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        // act
        ListBookings result = bookingService.getBookingHistory(null, null, 1L, 1L);

        // assert
        assertNotNull(result);
        List<GetBooking> bookings = result.bookings();

        assertNotNull(bookings);
        assertEquals(1, bookings.size());

        GetBooking getBooking = bookings.getFirst();
        assertEquals(bookingId1, getBooking.id());
        assertEquals(userId1, getBooking.userId());
        assertEquals(carId1, getBooking.carId());
        assertEquals(paymentId1, getBooking.paymentId());
        assertEquals(StatusBooking.NEW, getBooking.statusBooking());

        verify(bookingRepository).findAll();
    }

    @Test
    public void getBookingHistory_Pagination_SecondPage() {
        //Arrange
        UUID bookingId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID carId1 = UUID.randomUUID();
        UUID paymentId1 = UUID.randomUUID();

        Booking booking1 = new Booking();
        booking1.setId(bookingId1);
        booking1.setUserId(userId1);
        booking1.setStatusBooking(StatusBooking.NEW);
        booking1.setCarId(carId1);
        booking1.setPaymentId(paymentId1);

        UUID bookingId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID carId2 = UUID.randomUUID();
        UUID paymentId2 = UUID.randomUUID();

        Booking booking2 = new Booking();
        booking2.setId(bookingId2);
        booking2.setUserId(userId2);
        booking2.setStatusBooking(StatusBooking.BOOKED);
        booking2.setCarId(carId2);
        booking2.setPaymentId(paymentId2);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        // act
        ListBookings result = bookingService.getBookingHistory(null, null, 1L, 2L);

        // assert
        assertNotNull(result);
        List<GetBooking> bookings = result.bookings();

        assertNotNull(bookings);
        assertEquals(1, bookings.size());

        GetBooking getBooking = bookings.getLast();
        assertEquals(bookingId2, getBooking.id());
        assertEquals(StatusBooking.BOOKED, getBooking.statusBooking());
        assertEquals(userId2, getBooking.userId());
        assertEquals(carId2, getBooking.carId());
        assertEquals(paymentId2, getBooking.paymentId());

        verify(bookingRepository).findAll();
    }

    @Test
    public void getBookingHistory_RequestNonexistentPage_ShouldThrow() {
        //Arrange
        UUID bookingId1 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID carId1 = UUID.randomUUID();
        UUID paymentId1 = UUID.randomUUID();

        Booking booking = new Booking();
        booking.setId(bookingId1);
        booking.setUserId(userId1);
        booking.setStatusBooking(StatusBooking.NEW);
        booking.setCarId(carId1);
        booking.setPaymentId(paymentId1);

        when(bookingRepository.findAll()).thenReturn(Collections.singletonList(booking));

        // act & assert
        assertThrows(PaginationException.class, () -> {
            bookingService.getBookingHistory(null, null, 1L, 20L);
        });
    }
}