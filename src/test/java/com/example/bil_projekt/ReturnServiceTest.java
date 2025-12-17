package com.example.bil_projekt;

import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.model.RentalAgreement;
import com.example.bil_projekt.repository.CarRepository;
import com.example.bil_projekt.repository.RentalRepository;
import com.example.bil_projekt.repository.ReturnInspectionRepository;
import com.example.bil_projekt.service.ReturnService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReturnServiceTest {

    @Mock
    private CarRepository carRepo;

    @Mock
    private RentalRepository rentalRepo;

    @Mock
    private ReturnInspectionRepository inspectionRepo;

    @InjectMocks
    private ReturnService returnService;

    @Test
    void happyFlow_registerReturn_whenCarIsUdlejet() {
        // Arrange
        Car car = new Car(1, "STEL001", "Peugeot", "208", "", 0, 0, "Udlejet");
        RentalAgreement rental = new RentalAgreement(
                10,
                1,
                5,
                null,
                null,
                false,
                "København",
                2999.0
        );

        when(carRepo.findBySteelNumber("STEL001")).thenReturn(car);
        when(rentalRepo.findActiveByCarId(1)).thenReturn(rental);
        when(inspectionRepo.create(any())).thenReturn(100);

        int inspectionId = returnService.registerReturn("STEL001");

        assertEquals(100, inspectionId);
        verify(rentalRepo).closeRental(10);
        verify(carRepo).updateStatus(1, "Skadet");
        verify(inspectionRepo).create(any());
    }

    @Test
    void exceptionFlow_registerReturn_whenCarIsNotUdlejet() {
        // Arrange
        Car car = new Car(1, "STEL002", "VW", "Golf", "", 0, 0, "Ledig");
        when(carRepo.findBySteelNumber("STEL002")).thenReturn(car);

        assertThrows(
                IllegalStateException.class,
                () -> returnService.registerReturn("STEL002")
        );

        verify(rentalRepo, never()).closeRental(anyInt());
        verify(inspectionRepo, never()).create(any());
    }
}
