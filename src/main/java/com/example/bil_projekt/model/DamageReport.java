package com.example.bil_projekt.model;


public class DamageReport {
    private int damageId;
    private int inspectionId;
    public String description;
    public String severity;
    public double costEstimate;
    public String damageType;

    public DamageReport(int damageId, int inspectionId, String description, String severity,
                        double costEstimate, String damageType) {
        this.damageId = damageId;
        this.inspectionId = inspectionId;
        this.description = description;
        this.severity = severity;
        this.costEstimate = costEstimate;
        this.damageType = damageType;
    }

    public int getDamageId() {
        return damageId;
    }

    public int getInspectionId() {
        return inspectionId;
    }

    public String getDescription() {
        return description;
    }

    public String getSeverity() {
        return severity;
    }

    public double getCostEstimate() {
        return costEstimate;
    }

    public String getDamageType() {
        return damageType;
    }

}

