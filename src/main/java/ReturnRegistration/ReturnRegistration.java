package Deniz;

import java.time.LocalDate;

public class ReturnRegistration {

    public void registerReturn(Car car, RentalAgreement agreement, LocalDate returnDate) {

        // 1. her bliver der tjekket om bilen findes
        if (car == null) {
            System.out.println("Fejl: Bilen blev ikke fundet.");
            return;
        }

        // 2. her bliver der Tjekket om  lejeaftale findes
        if (agreement == null) {
            System.out.println("Fejl: Ingen aktiv lejeaftale fundet.");
            return;
        }

        // 3. også her tjekker vi at bilen er udlejet
        if (!car.getStatus().equals("Udlejet")) {
            System.out.println("Fejl: Bilen er ikke udlejet og kan derfor ikke tilbageleveres.");
            return;
        }

        // 4. Tjek at retur-dato ikke er før start, da den skal være efterstarts dato
        if (returnDate.isBefore(agreement.getStart_date())) {
            System.out.println("Fejl: Tilbageleveringsdato kan ikke ligge før lejeperiodens start.");
            return;
        }

        // 5. Registrer tilbagelevering i aftalen
        agreement.setEnd_date(returnDate);

        // 6. Opdater bilstatus
        car.setStatus("Tilbageleveret - Afventer skadesvurdering");

        // 7. Bekræftelse
        System.out.println("Bil " + car.getRegistration_number() +
                " er nu tilbageleveret.");
        System.out.println("Ny status: " + car.getStatus());
        System.out.println("Returneringsdato: " + returnDate);
    }
}
