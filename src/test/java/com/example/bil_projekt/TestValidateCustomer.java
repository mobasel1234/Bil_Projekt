package com.example.bil_projekt;

import com.example.bil_projekt.CustomerInfo.Customer;
import com.example.bil_projekt.Deniz.RentalAgreement;
import com.example.bil_projekt.Repository.CustomerRepository;
import com.example.bil_projekt.Service.RentalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class TestValidateCustomer {

    @Test
    void testValidateCustomer_() {
        CustomerRepository mockRepo = Mockito.mock(CustomerRepository.class);

        Mockito.when(mockRepo.exists(10)).thenReturn(true);

        Customer c = new Customer(10, "Ali Tester", "ali@gmail.com", "12345678", "Testvej");
        Mockito.when(mockRepo.findCustomerById(10)).thenReturn(c);

        RentalService service = new RentalService();
        service.setCustomerRepo(mockRepo);

        assertDoesNotThrow(() -> service.createRental(
                new RentalAgreement(1, 2, 10, LocalDate.now(),
                        LocalDate.now().plusMonths(2), true,
                        "Kbh")
        ));
    }
}
