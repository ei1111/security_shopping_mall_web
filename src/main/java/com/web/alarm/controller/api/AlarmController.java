package com.web.alarm.controller.api;

import com.web.alarm.service.AlarmService;
import com.web.alarm.service.ImproveAlarmService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm/v1")
public class AlarmController {

    private final AlarmService alarmServie;
    private final ImproveAlarmService improveAlarmService;


    @GetMapping("/post-alarm/{id}")
    public ResponseEntity<Void> postAlarm(@PathVariable("id") Long id) {
        alarmServie.postAlarm(id);
        improveAlarmService.postAlarm(id);
        //여러명에게 알람 보내기
        improveAlarmService.sendBookingConfirmationPush(Map.of(1L,1L));
        return ResponseEntity.ok().build();
    }
}
