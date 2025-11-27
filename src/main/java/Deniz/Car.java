package Deniz;

public class Car {

    private int car_id;
    private String registration_number;
    private String vin_number;
    private String brand;
    private String model;
    private String equipment;
    private double tax;
    private String status;   // 'Ledig' eller 'Udlejet'

    public Car() {}

    public Car(int car_id, String registration_number, String vin_number,
               String brand, String model, String equipment, double tax, String status) {
        this.car_id = car_id;
        this.registration_number = registration_number;
        this.vin_number = vin_number;
        this.brand = brand;
        this.model = model;
        this.equipment = equipment;
        this.tax = tax;
        this.status = status;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getVin_number() {
        return vin_number;
    }

    public void setVin_number(String vin_number) {
        this.vin_number = vin_number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

