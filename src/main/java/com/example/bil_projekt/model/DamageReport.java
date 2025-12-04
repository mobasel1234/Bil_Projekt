package com.example.bil_projekt.model;

public class DamageReport {


        private int damage_id;
        private int inspection_id;
        private String description;
        private String damage_type;
        private double severity;
        private double cost_estimate;

        public DamageReport() {}


        public int getDamage_id() { return damage_id; }
        public void setDamage_id(int damage_id) {
            this.damage_id = damage_id; }

        public int getInspection_id() { return inspection_id; }
        public void setInspection_id(int inspection_id) {
            this.inspection_id = inspection_id;
        }

        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }

        public String getDamage_type() {
            return damage_type;
        }
        public void setDamage_type(String damage_type) {
            this.damage_type = damage_type;
        }

        public double getSeverity() { return severity;
        }

        public void setSeverity(double severity) { this.severity = severity;
        }

        public double getCost_estimate() {
            return cost_estimate;
        }
        public void setCost_estimate(double cost_estimate) {
            this.cost_estimate = cost_estimate;
        }
    }


