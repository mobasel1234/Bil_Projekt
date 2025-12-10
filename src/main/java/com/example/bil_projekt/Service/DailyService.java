package com.example.bil_projekt.Service;

import com.example.bil_projekt.Repository.RentalRepository;
import com.example.bil_projekt.model.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyService {

    @Autowired
    private RentalRepository rentalRepo;

    public List<RentalAgreement> getReturnsToday() {
        return rentalRepo.findReturnsToday();
    }
}
