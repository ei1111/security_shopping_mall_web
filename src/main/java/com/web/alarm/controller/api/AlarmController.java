package com.web.alarm.controller.api;

import com.web.alarm.service.AlarmService;
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


    @GetMapping("/post-alarm/{id}")
    public ResponseEntity<Void> postAlarm(@PathVariable("id") Long id) {
        alarmServie.postAlarm(id);
        return ResponseEntity.ok().build();
    }
}
