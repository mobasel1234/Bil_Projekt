package com.example.bil_projekt.Service;

import com.example.bil_projekt.Repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepo;

    // Tjek og opret alarmer
    public void checkDamageAlarms() {
        List<Integer> cars = alarmRepo.findCarsInDamageOver3Days();

        for (int carId : cars) {
            alarmRepo.createAlarm(carId);
        }
    }
}
