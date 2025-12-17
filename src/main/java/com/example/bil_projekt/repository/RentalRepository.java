package com.example.bil_projekt.repository;


import com.example.bil_projekt.model.RentalAgreement;
import com.example.bil_projekt.model.RentalDetailsDTO;

import java.util.List;

public interface RentalRepository {
    int create(RentalAgreement rental);

    RentalAgreement findActiveByCarId(int carId);

    List<RentalAgreement> findActiveRentals();

    void closeRental(int rentalId); // sætter end_date = i dag

    RentalDetailsDTO findActiveRentalDetailsBySteelNumber(String steelNumber);

}
