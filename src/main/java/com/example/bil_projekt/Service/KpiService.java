package com.example.bil_projekt.Service;

import com.example.bil_projekt.Repository.car.CarRepository;
import com.example.bil_projekt.Repository.car.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KpiService {

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private RentalRepository rentalRepo;

    // KPI 1: Returnerer antal udlejede biler
    public int getRentedCarsCount() {
        return carRepo.countRentedCars();
    }

    // KPI 2: Returnerer samlet pris på aktive lejeaftaler
    public double getActiveRentalTotalPrice() {
        Double result = rentalRepo.sumActiveRentalPrices();
        return result != null ? result : 0.0;
    }
}
