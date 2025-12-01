package com.example.bil_projekt;

import com.example.bil_projekt.Service.CarService;
import com.example.bil_projekt.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {

    @Test
    void testCanCarBeBooked_HappyFlow() {
        RentalRepository mockRepo = Mockito.mock(RentalRepository.class);

        Mockito.when(mockRepo.IsCarAvailable(5)).thenReturn(true);

        CarService service = new CarService();
        service.rentalRepository = mockRepo;

        assertTrue(service.CanCarBeBooked(5));
    }

    @Test
    void testCanCarBeBooked_ExceptionFlow() {
        RentalRepository mockRepo = Mockito.mock(RentalRepository.class);

        Mockito.when(mockRepo.IsCarAvailable(5))
                .thenThrow(new RuntimeException("Database error"));

        CarService service = new CarService();
        service.rentalRepository = mockRepo;

        assertThrows(RuntimeException.class, () -> service.CanCarBeBooked(5));
    }

    @Test
    void testCarHasActiveAgreement_HappyFlow() {
        RentalRepository mockRepo = Mockito.mock(RentalRepository.class);

        Mockito.when(mockRepo.HasActiveAgreement(10)).thenReturn(true);

        CarService service = new CarService();
        service.rentalRepository = mockRepo;

        assertTrue(service.CarHasActiveAgreement(10));
    }

    @Test
    void testCarHasActiveAgreement_NoAgreement() {
        RentalRepository mockRepo = Mockito.mock(RentalRepository.class);

        Mockito.when(mockRepo.HasActiveAgreement(10)).thenReturn(false);

        CarService service = new CarService();
        service.rentalRepository = mockRepo;

        assertFalse(service.CarHasActiveAgreement(10));
    }
}
