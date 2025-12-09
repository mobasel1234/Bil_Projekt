package com.example.bil_projekt.model;

import java.time.LocalDate;

public class ReturnRegistration {

    public void registerReturn(Car car, RentalAgreement agreement, LocalDate returnDate) {

        // 1. Tjek om bil findes
        if (car == null) {
            System.out.println("Fejl: Bilen blev ikke fundet.");
            return;
        }

        // 2. Tjek om lejeaftale findes
        if (agreement == null) {
            System.out.println("Fejl: Ingen aktiv lejeaftale fundet.");
            return;
        }

        // 3. Tjek om bilen er udlejet
        if (!"Udlejet".equals(car.getStatus())) {
            System.out.println("Fejl: Bilen er ikke udlejet og kan derfor ikke tilbageleveres.");
            return;
        }

        // 4. Dato-check
        if (returnDate.isBefore(agreement.getStart_date())) {
            System.out.println("Fejl: Tilbageleveringsdato ligger før lejeperiodens start.");
            return;
        }

        // 5. Opdater slutdato
        agreement.setEnd_date(returnDate);

        // 6. Opdater bilstatus
        car.setStatus("Tilbageleveret - Afventer skadesvurdering");

        // 7. Bekræftelse
        System.out.println("Bil med stelnummer: " + car.getSteelNummer() + " er nu returneret.");
        System.out.println("Ny status: " + car.getStatus());
        System.out.println("Returneringsdato: " + returnDate);
    }
}
