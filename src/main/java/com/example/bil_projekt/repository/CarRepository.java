package com.example.bil_projekt.repository;


import com.example.bil_projekt.model.Car;

import java.util.List;

public interface CarRepository {
    List<Car> findAll();

    Car findById(int carId);

    void updateStatus(int carId, String status);

    Car findBySteelNumber(String steelNumber);

}
