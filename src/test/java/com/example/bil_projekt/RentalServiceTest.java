package com.example.bil_projekt;

import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.model.Customer;
import com.example.bil_projekt.model.RentalAgreement;
import com.example.bil_projekt.repository.CarRepository;
import com.example.bil_projekt.repository.CustomerRepository;
import com.example.bil_projekt.repository.RentalRepository;
import com.example.bil_projekt.service.RentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private CarRepository carRepo;

    @Mock
    private RentalRepository rentalRepo;

    @Mock
    private CustomerRepository customerRepo;

    // Mockito injicerer mocks i @Autowired-felterne
    @InjectMocks
    private RentalService rentalService;

    @Test
    void happyFlow_createRental_whenCarIsAvailable_andCustomerExists() {


        when(carRepo.findBySteelNumber("STEL001"))
                .thenReturn(new Car(
                        1,
                        "STEL001",
                        "Peugeot",
                        "208",
                        "Active",
                        0,
                        0,
                        "Ledig"
                ));

        when(customerRepo.findByEmail("test@test.dk"))
                .thenReturn(new Customer(
                        10,
                        "Test Kunde",
                        "test@test.dk",
                        "12345678",
                        "Testvej 1"
                ));

        when(rentalRepo.create(any(RentalAgreement.class)))
                .thenReturn(42);

        // Act
        int rentalId = rentalService.createRental(
                "STEL001",
                "Test Kunde",
                "test@test.dk",
                "12345678",
                "Testvej 1",
                2999.0
        );


        assertEquals(42, rentalId);
        verify(carRepo).updateStatus(1, "Udlejet");
        verify(rentalRepo).create(any(RentalAgreement.class));
    }

    @Test
    void happyFlow_createRental_whenCustomerDoesNotExist() {


        when(carRepo.findBySteelNumber("STEL002"))
                .thenReturn(new Car(
                        2,
                        "STEL002",
                        "VW",
                        "Golf",
                        "Highline",
                        0,
                        0,
                        "Ledig"
                ));

        when(customerRepo.findByEmail("new@test.dk"))
                .thenThrow(EmptyResultDataAccessException.class);

        when(customerRepo.create(any(Customer.class)))
                .thenReturn(99);

        when(rentalRepo.create(any(RentalAgreement.class)))
                .thenReturn(77);


        int rentalId = rentalService.createRental(
                "STEL002",
                "Ny Kunde",
                "new@test.dk",
                "87654321",
                "Nyvej 5",
                2499.0
        );


        assertEquals(77, rentalId);
        verify(customerRepo).create(any(Customer.class));
        verify(carRepo).updateStatus(2, "Udlejet");
    }

    @Test
    void exceptionFlow_createRental_whenCarIsNotAvailable() {

        // Arrange
        when(carRepo.findBySteelNumber("STEL003"))
                .thenReturn(new Car(
                        3,
                        "STEL003",
                        "Audi",
                        "A3",
                        "S-Line",
                        0,
                        0,
                        "Udlejet"
                ));


        assertThrows(
                IllegalStateException.class,
                () -> rentalService.createRental(
                        "STEL003",
                        "Test",
                        "test@test.dk",
                        "11111111",
                        "Testvej 2",
                        3000.0
                )
        );

        verify(rentalRepo, never()).create(any());
        verify(carRepo, never()).updateStatus(anyInt(), anyString());
    }

    @Test
    void exceptionFlow_createRental_whenMonthlyPriceIsNegative() {

        // Arrange
        when(carRepo.findBySteelNumber("STEL004"))
                .thenReturn(new Car(
                        4,
                        "STEL004",
                        "Toyota",
                        "Corolla",
                        "Hybrid",
                        0,
                        0,
                        "Ledig"
                ));

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> rentalService.createRental(
                        "STEL004",
                        "Test",
                        "test@test.dk",
                        "22222222",
                        "Testvej 3",
                        -100.0
                )
        );

        verify(rentalRepo, never()).create(any());
        verify(carRepo, never()).updateStatus(anyInt(), anyString());
    }
}

