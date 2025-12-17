package com.example.bil_projekt.service;

import com.example.bil_projekt.model.DamageReport;
import com.example.bil_projekt.repository.DamageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DamageService {

    // Skadeservice håndterer validering + gemning af skader.
    // Repositories står kun for SQL og mapping til objekter.
    @Autowired
    private DamageRepository damageRepo;

    public void addDamages(int inspectionId, List<DamageReport> damages) {
        // ADT: vi bruger en Collection (ArrayList) og itererer over skaderne.
        List<DamageReport> safe = new ArrayList<>(damages);

        for (DamageReport d : safe) {
            if (d.getCostEstimate() < 0) {
                throw new IllegalArgumentException("Skadepris må ikke være negativ");
            }
            damageRepo.create(d);
        }
    }
}
