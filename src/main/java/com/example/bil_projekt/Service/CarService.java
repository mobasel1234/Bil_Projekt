package com.example.bil_projekt.Service;

import com.example.bil_projekt.Repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    public RentalRepository rentalRepository;

    public void setRentalRepository(RentalRepository repo) {
        this.rentalRepository = repo;
    }

    // Returnerer true hvis bilen er ledig
    public boolean CanCarBeBooked(int carId) {
        return rentalRepository.IsCarAvailable(carId);
    }

    public boolean CarHasActiveAgreement(int carId) {
        return rentalRepository.HasActiveAgreement(carId);
    }

}


