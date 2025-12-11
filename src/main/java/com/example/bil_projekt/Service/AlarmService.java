package com.example.bil_projekt.Service;

import com.example.bil_projekt.Repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepo;

    // 🔔 Alarm: Bil i SKADET > 3 dage
    public void checkDamageAlarms() {
        List<Integer> cars = alarmRepo.findCarsInDamageOver3Days();

        for (int carId : cars) {
            alarmRepo.createAlarm(carId, "SKADE OVER 3 DAGE");  // ✅ KORREKT
        }
    }


    // 🔔 Alarm: DS3 < 5 biler
    public void checkDS3Alarm() {
        int count = alarmRepo.countDS3Cars();

        if (count < 5) {
            alarmRepo.createDS3Alarm();
        }
    }

    // ✅ DEN DU MANGLER – bruges i controlleren
    public void checkAllAlarms() {
        checkDamageAlarms();
        checkDS3Alarm();
    }
}
