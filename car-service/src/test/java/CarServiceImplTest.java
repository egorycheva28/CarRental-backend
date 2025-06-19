import com.example.carservice.dto.requests.EditCar;
import com.example.carservice.dto.response.GetCar;
import com.example.carservice.dto.response.ListCars;
import com.example.carservice.dto.response.SuccessResponse;
import com.example.carservice.exception.CarNotFoundException;
import com.example.carservice.exception.PaginationException;
import com.example.carservice.model.Car;
import com.example.carservice.model.Status;
import com.example.carservice.repository.CarRepository;
import com.example.carservice.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCars_NoStatus_FilterAll() {
        // arrange
        UUID carId1 = UUID.randomUUID();

        Car car1 = new Car();
        car1.setId(carId1);
        car1.setName("Car1");
        car1.setPrice(1L);
        car1.setStatus(Status.FREE);
        car1.setEditDate(new Date());

        UUID carId2 = UUID.randomUUID();

        Car car2 = new Car();
        car2.setId(carId2);
        car2.setName("Car2");
        car2.setPrice(1L);
        car2.setStatus(Status.UNDER_REPAIR);
        car2.setEditDate(new Date());

        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        // act
        ListCars result = carService.getCars(null, 10L, 1L);

        // assert
        assertNotNull(result);
        List<GetCar> cars = result.cars();

        assertNotNull(cars);
        assertEquals(2, cars.size());

        GetCar getCar1 = cars.getFirst();
        assertEquals("Car1", getCar1.name());
        assertEquals(Status.FREE, getCar1.status());
        assertEquals(1L, getCar1.price());
        assertEquals(carId1, getCar1.id());

        GetCar getCar2 = cars.getLast();
        assertEquals("Car2", getCar2.name());
        assertEquals(Status.UNDER_REPAIR, getCar2.status());
        assertEquals(1L, getCar2.price());
        assertEquals(carId2, getCar2.id());

        verify(carRepository).findAll();
    }

    @Test
    void testGetCars_WithStatus_FilterByStatus() {
        // arrange
        UUID carId1 = UUID.randomUUID();

        Car car1 = new Car();
        car1.setId(carId1);
        car1.setName("Car1");
        car1.setPrice(1L);
        car1.setStatus(Status.FREE);
        car1.setEditDate(new Date());

        UUID carId2 = UUID.randomUUID();

        Car car2 = new Car();
        car2.setId(carId2);
        car2.setName("Car2");
        car2.setPrice(1L);
        car2.setStatus(Status.UNDER_REPAIR);
        car2.setEditDate(new Date());

        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        // act
        ListCars result = carService.getCars(Status.FREE, 10L, 1L);

        // assert
        assertNotNull(result);
        List<GetCar> cars = result.cars();

        assertNotNull(cars);
        assertEquals(1, cars.size());

        GetCar getCar1 = cars.getFirst();
        assertEquals("Car1", getCar1.name());
        assertEquals(Status.FREE, getCar1.status());
        assertEquals(1L, getCar1.price());
        assertEquals(carId1, getCar1.id());

        verify(carRepository).findAll();
    }

    @Test
    void testGetCars_Pagination_FirstPage() {
        // arrange
        UUID carId1 = UUID.randomUUID();

        Car car1 = new Car();
        car1.setId(carId1);
        car1.setName("Car1");
        car1.setPrice(1L);
        car1.setStatus(Status.FREE);
        car1.setEditDate(new Date());

        UUID carId2 = UUID.randomUUID();

        Car car2 = new Car();
        car2.setId(carId2);
        car2.setName("Car2");
        car2.setPrice(1L);
        car2.setStatus(Status.FREE);
        car2.setEditDate(new Date());

        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        // act
        ListCars result = carService.getCars(Status.FREE, 1L, 1L);

        // assert
        assertNotNull(result);
        List<GetCar> cars = result.cars();

        assertNotNull(cars);
        assertEquals(1, cars.size());

        GetCar getCar = cars.getFirst();
        assertEquals("Car1", getCar.name());
        assertEquals(Status.FREE, getCar.status());
        assertEquals(1L, getCar.price());
        assertEquals(carId1, getCar.id());
    }

    @Test
    void testGetCars_Pagination_SecondPage() {
        // arrange
        UUID carId1 = UUID.randomUUID();

        Car car1 = new Car();
        car1.setId(carId1);
        car1.setName("Car1");
        car1.setPrice(1L);
        car1.setStatus(Status.FREE);
        car1.setEditDate(new Date());

        UUID carId2 = UUID.randomUUID();

        Car car2 = new Car();
        car2.setId(carId2);
        car2.setName("Car2");
        car2.setPrice(1L);
        car2.setStatus(Status.FREE);
        car2.setEditDate(new Date());

        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        // act
        ListCars result = carService.getCars(Status.FREE, 1L, 2L);

        // assert
        assertNotNull(result);
        List<GetCar> cars = result.cars();

        assertNotNull(cars);
        assertEquals(1, cars.size());

        GetCar getCar = cars.getLast();
        assertEquals("Car2", getCar.name());
        assertEquals(Status.FREE, getCar.status());
        assertEquals(1L, getCar.price());
        assertEquals(carId2, getCar.id());
    }

    @Test
    void testGetCars_RequestNonexistentPage_ShouldThrow() {
        // arrange
        UUID carId = UUID.randomUUID();
        Car car = new Car();
        car.setId(carId);
        car.setName("car");
        car.setStatus(Status.FREE);
        car.setPrice(2L);
        when(carRepository.findAll()).thenReturn(Collections.singletonList(car));

        // act & assert
        assertThrows(PaginationException.class, () -> {
            carService.getCars(Status.FREE, 1L, 2L);
        });
    }

    @Test
    public void testGetCarById_CarExists() {
        // Arrange
        UUID carId = UUID.randomUUID();
        Car car = new Car();
        car.setName("car");
        car.setStatus(Status.FREE);
        car.setPrice(2L);
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        // Act
        GetCar result = carService.getCarById(carId);

        // Assert
        assertNotNull(result);
        assertEquals(car.getId(), result.id());
        assertEquals("car", result.name());
        assertEquals(Status.FREE, result.status());
        assertEquals(2L, result.price());

        verify(carRepository, times(1)).findById(carId);
    }

    @Test
    public void testGetCarById_CarNotFound() {
        // Arrange
        UUID carId = UUID.randomUUID();
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CarNotFoundException.class, () -> {
            carService.getCarById(carId);
        });

        verify(carRepository, times(1)).findById(carId);
    }

    @Test
    void testEditCar_CarExists() {
        UUID carId = UUID.randomUUID();
        Car car = new Car();
        car.setName("car");
        car.setStatus(Status.FREE);
        car.setPrice(2L);

        EditCar editCar = new EditCar("New car", 1L);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SuccessResponse response = carService.editCar(carId, editCar);

        assertNotNull(response);
        assertEquals("Данные о машине успешно изменены!", response.message());
        assertEquals("New car", car.getName());
        assertEquals(1, car.getPrice());
        assertNotNull(car.getEditDate());

        verify(carRepository).findById(carId);
        verify(carRepository).save(car);
    }

    @Test
    void testEditCar_CarNotFound() {
        UUID carId = UUID.randomUUID();

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        EditCar editCar = new EditCar("New car", 1L);

        assertThrows(CarNotFoundException.class, () -> {
            carService.editCar(carId, editCar);
        });

        verify(carRepository).findById(carId);
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    void testStatusRepair_CarExists() {
        UUID carId = UUID.randomUUID();
        Car car = new Car();
        car.setName("car");
        car.setStatus(Status.FREE);
        car.setPrice(2L);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SuccessResponse response = carService.statusRepair(carId, Status.UNDER_REPAIR);

        assertNotNull(response);
        assertEquals("Статус машины успешно изменен!", response.message());
        assertEquals(Status.UNDER_REPAIR, car.getStatus());
        assertNotNull(car.getEditDate());

        verify(carRepository).findById(carId);
        verify(carRepository).save(car);
    }

    @Test
    void testStatusRepair_CarNotFound() {
        UUID carId = UUID.randomUUID();

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> {
            carService.statusRepair(carId, Status.UNDER_REPAIR);
        });

        verify(carRepository).findById(carId);
        verify(carRepository, never()).save(any(Car.class));
    }
}

