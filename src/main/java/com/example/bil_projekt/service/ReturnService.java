package com.example.bil_projekt.service;

import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.model.RentalAgreement;
import com.example.bil_projekt.model.ReturnInspection;
import com.example.bil_projekt.repository.CarRepository;
import com.example.bil_projekt.repository.RentalRepository;
import com.example.bil_projekt.repository.ReturnInspectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReturnService {

    // Return-flowet er en central use case:
    // Udlejet -> tilbageleveret -> klar til skadeoprettelse.
    @Autowired
    private CarRepository carRepo;

    @Autowired
    private RentalRepository rentalRepo;

    @Autowired
    private ReturnInspectionRepository inspectionRepo;

    public int registerReturn(String steelNumber) {
        Car car = carRepo.findBySteelNumber(steelNumber);
        int carId = car.getCarId();

        // Forretningsregel: kun udlejede biler kan returneres.
        if (!"Udlejet".equalsIgnoreCase(car.getStatus())) {
            throw new IllegalStateException("Kun udlejede biler kan returneres");
        }

        RentalAgreement active = rentalRepo.findActiveByCarId(carId);
        if (active == null) {
            throw new IllegalStateException("Ingen aktiv lejeaftale fundet på bilen");
        }

        // Vi lukker lejen ved at sætte end_date = i dag.
        rentalRepo.closeRental(active.getRentalId());

        // Vi opretter en inspektion som kobler returnering til den specifikke lejeaftale.
        ReturnInspection inspection = new ReturnInspection(
                0,
                carId,
                active.getRentalId(),
                LocalDate.now(),
                null,
                "Bilabonnement",
                ""
        );

        int inspectionId = inspectionRepo.create(inspection);

        // Vi sætter bilen til "Skadet" for at signalere den skal gennem skadesregistrering.
        // (I MVP'en bruger vi én status til "afventer skade".)
        carRepo.updateStatus(carId, "Skadet");

        return inspectionId;
    }
}
