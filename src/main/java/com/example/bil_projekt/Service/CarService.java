package com.example.bil_projekt.Service;

import com.example.bil_projekt.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private RentalRepository rentalRepository;

    // Returnerer true hvis bilen er ledig
    public boolean canCarBeBooked(int carId) {
        return rentalRepository.isCarAvailable(carId);
    }
}


