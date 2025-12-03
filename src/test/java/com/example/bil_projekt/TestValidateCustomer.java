package com.example.bil_projekt;


import com.example.bil_projekt.CarBuisness.Car;
import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.RentalRepository;
import com.example.bil_projekt.CustomerInfo.Customer;
import com.example.bil_projekt.Repository.CustomerRepository;
import com.example.bil_projekt.Service.RentalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class TestValidateCustomer {

    @Test
    void testValidateCustomer_HappyFlow() {


        CustomerRepository mockCustomerRepo = Mockito.mock(CustomerRepository.class);
        CarRepository mockCarRepo = Mockito.mock(CarRepository.class);
        RentalRepository mockRentalRepo = Mockito.mock(RentalRepository.class);

        // ------------ MOCK CUSTOMER ------------
        Mockito.when(mockCustomerRepo.exists(10)).thenReturn(true);

        Customer c = new Customer(10, "Ali Tester", "ali@gmail.com", "12345678", "Testvej");
        Mockito.when(mockCustomerRepo.findCustomerById(10)).thenReturn(c);

        // ------------ MOCK CAR ------------
        Car car = new Car();
        car.setCar_id(2);
        car.setStatus("Ledig");

        Mockito.when(mockCarRepo.findByReg("VIN001")).thenReturn(car);

        // ------------ MOCK RENTAL INSERT ------------
        Mockito.doNothing().when(mockRentalRepo).createRental(Mockito.any());

        // ------------ SERVICE ------------
        RentalService service = new RentalService();

        // Brug setters (test injection)
        service.setCustomerRepo(mockCustomerRepo);
        service.setCarRepo(mockCarRepo);
        service.setRentalRepo(mockRentalRepo);

        // ------------ ACT: RUN THE METHOD ------------
        assertDoesNotThrow(() ->
                service.createRental(
                        "VIN001",
                        10,
                        "2025-01-01",
                        "2025-03-01",
                        true,
                        "Kbh"
                )
        );
    }
}
