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

    // ✅ Test 1: Metoden må ikke crashe og skal returnere en liste
    @Test
    void testGetReturnsTodayDoesNotCrash() {
        List<RentalAgreement> result = dailyService.getReturnsToday();

        assertNotNull(result); // altid en liste (evt. tom)
    }

    // ✅ Test 2: Hvis der er returneringer i dag, skal data være gyldig
    @Test
    void testReturnsTodayLogic() {
        List<RentalAgreement> result = dailyService.getReturnsToday();

        if (!result.isEmpty()) {
            for (RentalAgreement r : result) {
                assertTrue(r.getRental_id() > 0);
                assertTrue(r.getCar_id() > 0);
            }
        }
    }
}
