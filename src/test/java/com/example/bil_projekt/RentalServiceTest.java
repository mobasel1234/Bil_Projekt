package com.example.bil_projekt;

import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.model.Customer;
import com.example.bil_projekt.repository.CarRepository;
import com.example.bil_projekt.repository.CustomerRepository;
import com.example.bil_projekt.repository.RentalRepository;
import com.example.bil_projekt.service.RentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private CarRepository carRepo;

    @Mock
    private RentalRepository rentalRepo;

    @Mock
    private CustomerRepository customerRepo;

    // Mockito injicerer automatisk mocks i @Autowired felter
    @InjectMocks
    private RentalService service;

    @Test
    void happyFlow_createRental_whenCarIsAvailable() {

        when(carRepo.findBySteelNumber("STEL029"))
                .thenReturn(new Car(1, "STEL029", "Peugeot", "208", "", 0, 0, "Ledig"));

        when(customerRepo.findByEmail("lars24@gmail.com"))
                .thenReturn(new Customer(10, "Lars", "lars24@gmail.com", "12345678", "Testvej 1"));

        when(rentalRepo.create(any())).thenReturn(42);

        int rentalId = service.createRental(
                "STEL029",
                "Lars",
                "lars24@gmail.com",
                "12345678",
                "Testvej 1",
                2999.0
        );

        assertEquals(42, rentalId);
        verify(carRepo).updateStatus(1, "Udlejet");
    }

    @Test
    void exceptionFlow_createRental_whenCarNotAvailable() {

        when(carRepo.findBySteelNumber("STEL030"))
                .thenReturn(new Car(1, "STEL030", "VW", "Golf", "", 0, 0, "Udlejet"));

        when(customerRepo.findByEmail("test@gmail.com"))
                .thenReturn(new Customer(10, "Test", "test@gmail.com", "12345678", "Testvej 1"));

        assertThrows(IllegalStateException.class, () ->
                service.createRental(
                        "STEL030",
                        "Test",
                        "test@gmail.com",
                        "12345678",
                        "Testvej 1",
                        2500.0
                )
        );

        verify(rentalRepo, never()).create(any());
        verify(carRepo, never()).updateStatus(anyInt(), anyString());
    }
}
