package com.example.bil_projekt;


import com.example.bil_projekt.Repository.InventoryEventRepository;
import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.Repository.car.CarRepository;
import com.example.bil_projekt.Repository.car.RentalRepository;
import com.example.bil_projekt.model.Customer;
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
        InventoryEventRepository mockInventoryRepo = Mockito.mock(InventoryEventRepository.class);

        // -------- MOCK CAR --------
        Car car = new Car();
        car.setCar_id(2);
        car.setStatus("Ledig");

        Mockito.when(mockCarRepo.findByReg("VIN001")).thenReturn(car);

        // -------- MOCK CUSTOMER CREATE --------
        Mockito.when(mockCustomerRepo.createCustomer(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
        )).thenReturn(10);

        // -------- MOCK RENTAL INSERT --------
        Mockito.doNothing().when(mockRentalRepo).createRental(Mockito.any());

        // -------- SERVICE --------
        RentalService service = new RentalService();
        service.setCarRepo(mockCarRepo);
        service.setCustomerRepo(mockCustomerRepo);
        service.setRentalRepo(mockRentalRepo);
        service.setInventoryRepo(mockInventoryRepo);

        // -------- ACT --------
        assertDoesNotThrow(() ->
                service.createRental(
                        "VIN001",
                        "Ali Tester",
                        "ali@gmail.com",
                        "12345678",
                        "Testvej",
                        "2025-01-01",
                        "2025-03-01",
                        true,
                        "Kbh"
                )
        );
    }
}
