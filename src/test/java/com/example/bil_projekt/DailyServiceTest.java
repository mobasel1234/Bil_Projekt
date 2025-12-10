package com.example.bil_projekt;

import com.example.bil_projekt.Service.DailyService;
import com.example.bil_projekt.model.RentalAgreement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DailyServiceTest {

    @Autowired
    private DailyService dailyService;

    @Test
    void testGetReturnsTodayDoesNotCrash() {
        List<RentalAgreement> result = dailyService.getReturnsToday();

        // Vi tjekker at metoden returnerer en liste
        assertNotNull(result);
    }

    @Test
    void testReturnsTodayLogic() {
        List<RentalAgreement> result = dailyService.getReturnsToday();

        // Hvis der ER data i DB for i dag → skal den kunne findes
        for (RentalAgreement r : result) {
            assertNotNull(r.getRental_id());
            assertNotNull(r.getCar_id());
        }
    }
}
