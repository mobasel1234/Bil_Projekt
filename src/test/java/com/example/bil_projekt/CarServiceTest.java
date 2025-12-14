package com.example.bil_projekt;

import com.example.bil_projekt.Service.CarService;
import com.example.bil_projekt.Repository.car.RentalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {

    @Test
    void testCanCarBeBooked_HappyFlow() {
        RentalRepository mockRepo = Mockito.mock(RentalRepository.class);

        Mockito.when(mockRepo.isCarAvailable(5)).thenReturn(true);

        CarService service = new CarService();
        service.setRentalRepository(mockRepo);

        assertTrue(service.canCarBeBooked(5));
    }

    @Test
    void testCanCarBeBooked_ExceptionFlow() {
        RentalRepository mockRepo = Mockito.mock(RentalRepository.class);

        Mockito.when(mockRepo.isCarAvailable(5))
                .thenThrow(new RuntimeException("Database error"));

        CarService service = new CarService();
        service.setRentalRepository(mockRepo);

        assertThrows(RuntimeException.class, () -> service.canCarBeBooked(5));
    }

    @Test
    void testCarHasActiveAgreement_HappyFlow() {
        RentalRepository mockRepo = Mockito.mock(RentalRepository.class);

        Mockito.when(mockRepo.hasActiveAgreement(10)).thenReturn(true);

        CarService service = new CarService();
        service.setRentalRepository(mockRepo);

        assertTrue(service.carHasActiveAgreement(10));
    }

    @Test
    void testCarHasActiveAgreement_NoAgreement() {
        RentalRepository mockRepo = Mockito.mock(RentalRepository.class);

        Mockito.when(mockRepo.hasActiveAgreement(10)).thenReturn(false);

        CarService service = new CarService();
        service.setRentalRepository(mockRepo);

        assertFalse(service.carHasActiveAgreement(10));
    }
}
