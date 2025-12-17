package com.example.bil_projekt.repository;

import com.example.bil_projekt.model.DamageReport;

import java.util.List;

public interface DamageRepository {
    void create(DamageReport damage);

    List<DamageReport> findByInspectionId(int inspectionId);
}
