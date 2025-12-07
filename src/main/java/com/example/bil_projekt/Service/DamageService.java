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
    private CarRepository carRepo;   // ✅ TILFØJET

    public double calculatePrice(String type, double severity) {
        DamageMatrix m = matrixRepo.findByType(type);
        return m.getPrice() * severity;
    }

    public void registerDamage(int inspectionId,
                               String description,
                               String type,
                               double severity) {

        // 1. Beregn pris
        double price = calculatePrice(type, severity);

        // 2. Opret skaderapport
        DamageReport r = new DamageReport();
        r.setInspection_id(inspectionId);
        r.setDescription(description);
        r.setDamage_type(type);
        r.setSeverity(severity);
        r.setCost_estimate(price);

        damageRepo.createDamageReport(r);

        // ✅ 3. OPDATER BILSTATUS EFTER SKADE
        int carId = carRepo.findCarIdByInspectionId(inspectionId);

        if (price == 0) {
            carRepo.updateStatus(carId, "Klar til udlejning");
        } else {
            carRepo.updateStatus(carId, "På værksted");
        }
    }
}
