package com.example.bil_projekt.Service;

import com.example.bil_projekt.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KpiService {

    @Autowired
    private CarRepository carRepo;

    // Returnerer antal udlejede biler
    public int getRentedCarsCount() {
        return carRepo.countRentedCars();
    }
}
