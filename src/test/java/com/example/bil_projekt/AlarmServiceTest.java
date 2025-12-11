package com.example.bil_projekt;

import com.example.bil_projekt.Repository.AlarmRepository;
import com.example.bil_projekt.Service.AlarmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {

    @Mock
    private AlarmRepository alarmRepo;

    @InjectMocks
    private AlarmService alarmService;

    // ✅ Test 1: Skadealarm oprettes
    @Test
    void testDamageAlarmCreated() {
        when(alarmRepo.findCarsInDamageOver3Days()).thenReturn(List.of(1));

        alarmService.checkDamageAlarms();

        verify(alarmRepo).createAlarm(1, "SKADE OVER 3 DAGE");

    }

    // ✅ Test 2: DS3-alarm oprettes når under 5
    @Test
    void testDS3AlarmCreatedWhenBelow5() {
        when(alarmRepo.countDS3Cars()).thenReturn(3);

        alarmService.checkDS3Alarm();

        verify(alarmRepo).createDS3Alarm();
    }

    // ✅ Test 3: DS3-alarm oprettes IKKE når nok biler
    @Test
    void testDS3AlarmNotCreatedWhenEnoughCars() {
        when(alarmRepo.countDS3Cars()).thenReturn(7);

        alarmService.checkDS3Alarm();

        verify(alarmRepo, never()).createDS3Alarm();
    }
}
