package com.example.bil_projekt;

import com.example.bil_projekt.Service.AlarmService;
import com.example.bil_projekt.Repository.AlarmRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AlarmServiceTest {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private AlarmRepository alarmRepository;

    @Test
    void testDamageAlarmScanRunsWithoutCrash() {
        // Act
        alarmService.checkDamageAlarms();

        // Assert – vi tester at systemet kører fejlfrit
        assertTrue(true, "Alarm scan kørte uden fejl");
    }

    @Test
    void testCarsInDamageOver3DaysQuery() {
        var result = alarmRepository.findCarsInDamageOver3Days();

        // Vi tester bare at listen ikke er NULL (må gerne være tom)
        assertNotNull(result);
    }
}
