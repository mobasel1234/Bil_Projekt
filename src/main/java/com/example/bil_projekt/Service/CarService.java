package com.example.bil_projekt.Service;

import com.example.bil_projekt.Repository.car.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {


        @Autowired
        private RentalRepository rentalRepository;

        public void setRentalRepository(RentalRepository repo) {
            this.rentalRepository = repo;
        }

        public boolean canCarBeBooked(int carId) {
            return rentalRepository.isCarAvailable(carId);
        }

        public boolean carHasActiveAgreement(int carId) {
            return rentalRepository.hasActiveAgreement(carId);
        }
    }




