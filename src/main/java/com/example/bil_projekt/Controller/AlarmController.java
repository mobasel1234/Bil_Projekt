package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @GetMapping("/alarm/damage-check")
    @ResponseBody
    public String runDamageAlarmCheck() {
        alarmService.checkDamageAlarms();
        return "Skade-alarm scan gennemført!";
    }

    @GetMapping("/alarm/ds3-check")
    @ResponseBody
    public String runDS3AlarmCheck() {
        alarmService.checkDS3Alarm();
        return "DS3 alarm scan gennemført!";
    }

    @GetMapping("/alarm/check-all")
    @ResponseBody
    public String runAllAlarms() {
        alarmService.checkAllAlarms();
        return "ALLE alarmer opdateret!";
    }
}
