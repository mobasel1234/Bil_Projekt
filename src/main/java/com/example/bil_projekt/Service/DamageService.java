package com.example.bil_projekt.Service;

import com.example.bil_projekt.model.DamageMatrix;
import com.example.bil_projekt.model.DamageReport;
import com.example.bil_projekt.Repository.DamageMatrixRepository;
import com.example.bil_projekt.Repository.DamageRepository;
import com.example.bil_projekt.Repository.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageService {

    @Autowired
    private DamageRepository damageRepo;

    @Autowired
    private DamageMatrixRepository matrixRepo;

    @Autowired
    private CarRepository carRepo;

    // =======================
    // BEREGN PRIS
    // =======================
    public double calculatePrice(String type, double severity) {

        DamageMatrix m = matrixRepo.findByType(type);
        if (m == null) {
            throw new IllegalArgumentException(
                    "Ingen damage matrix fundet for type: " + type
            );
        }

        return m.getPrice() * severity;
    }

    // =======================
    // REGISTRER SKADE
    // =======================
    public void registerDamage(
            int inspectionId,
            String description,
            String type,
            double severity
    ) {

        int carId = carRepo.findCarIdByInspectionId(inspectionId);

        double price = calculatePrice(type, severity);

        DamageReport r = new DamageReport();
        r.setInspection_id(inspectionId);
        r.setDescription(description);
        r.setDamage_type(type);
        r.setSeverity(severity);
        r.setCost_estimate(price);

        damageRepo.createDamageReport(r);


        if (price == 0) {
            carRepo.updateStatus(carId, "Klar til udlejning");
        } else {
            carRepo.updateStatus(carId, "På værksted");
        }
    }

    // =======================
    // APPLY DAMAGE MATRIX
    // =======================
    public void applyDamageMatrix(int inspectionId, int matrixId) {

        DamageMatrix matrix = matrixRepo.findById(matrixId);

        int carId = carRepo.findCarIdByInspectionId(inspectionId);

        damageRepo.updateCostByInspection(
                inspectionId,
                matrix.getPrice()
        );

        carRepo.updateStatus(carId, "Skadet");
    }
}
