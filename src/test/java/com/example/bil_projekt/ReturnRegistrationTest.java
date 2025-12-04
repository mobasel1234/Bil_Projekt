package com.example.bil_projekt;

import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.model.RentalAgreement;
import com.example.bil_projekt.model.ReturnRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReturnRegistrationTest {

    private ReturnRegistration returnRegistration;
    private Car car;
    private RentalAgreement agreement;

    @BeforeEach
    void setUp() {
        returnRegistration = new ReturnRegistration();

        car = new Car();
        car.setCar_id(1);
        car.setSteelNummer("VIN123");
        car.setStatus("Udlejet");

        agreement = new RentalAgreement();
        agreement.setRental_id(10);
        agreement.setCar_id(1);
        agreement.setStart_date(LocalDate.of(2025, 1, 1));
        agreement.setEnd_date(LocalDate.of(2025, 1, 10));
    }

    // ----------- HAPPY FLOW -----------
    @Test
    void testRegisterReturn_HappyFlow() {
        LocalDate returnDate = LocalDate.of(2025, 1, 5);

        returnRegistration.registerReturn(car, agreement, returnDate);

        assertEquals(returnDate, agreement.getEnd_date());
        assertEquals("Tilbageleveret - Afventer skadesvurdering", car.getStatus());
    }

    // ----------- CAR = NULL -----------
    @Test
    void testRegisterReturn_CarIsNull() {
        LocalDate returnDate = LocalDate.of(2025, 1, 5);

        returnRegistration.registerReturn(null, agreement, returnDate);

        // Ingen ændringer må ske
        assertEquals(LocalDate.of(2025, 1, 10), agreement.getEnd_date());
    }

    // ----------- AGREEMENT = NULL -----------
    @Test
    void testRegisterReturn_AgreementIsNull() {
        LocalDate returnDate = LocalDate.of(2025, 1, 5);

        returnRegistration.registerReturn(car, null, returnDate);

        assertEquals("Udlejet", car.getStatus());
    }

    // ----------- BIL IKKE UDLEJET -----------
    @Test
    void testRegisterReturn_CarNotRented() {
        car.setStatus("Ledig");
        LocalDate returnDate = LocalDate.of(2025, 1, 5);

        returnRegistration.registerReturn(car, agreement, returnDate);

        assertEquals(LocalDate.of(2025, 1, 10), agreement.getEnd_date());
        assertEquals("Ledig", car.getStatus());
    }

    // ----------- FORKERT DATO -----------
    @Test
    void testRegisterReturn_InvalidReturnDate() {
        LocalDate invalidDate = LocalDate.of(2024, 12, 25);

        returnRegistration.registerReturn(car, agreement, invalidDate);

        assertEquals(LocalDate.of(2025, 1, 10), agreement.getEnd_date());
        assertEquals("Udlejet", car.getStatus());
    }
}

