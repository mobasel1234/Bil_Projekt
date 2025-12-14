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




    public double calculatePrice(String type, double severity) {
        DamageMatrix m = matrixRepo.findByType(type);
        return m.getPrice() * severity;
    }

    public void registerDamage(
            int inspectionId,
            String description,
            String type,
            double severity
    ) {





        // FINDER CAR_ID VIA INSPECTION
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
            carRepo.updateStatus(inspectionId, "Klar til udlejning");
        } else {
            carRepo.updateStatus(inspectionId, "På værksted");
        }
    }
}

