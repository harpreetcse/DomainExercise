package com.domainexcercise.microservices.restful.eventstore.controller;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domainexcercise.microservices.restful.eventstore.model.AttendanceEvent;
import com.domainexcercise.microservices.restful.eventstore.model.EventType;
import com.domainexcercise.microservices.restful.eventstore.repository.AttendanceEventRepository;
import com.domainexcercise.microservices.restful.eventstore.service.KafkaProducer;


@RestController
@RequestMapping("/api/swipe")
public class SwipeController {


    @Autowired
    private AttendanceEventRepository attendanceEventRepository;

    @PostMapping("/in/{employeeId}")
    public ResponseEntity<?> swipeIn(@PathVariable Long employeeId) {
        // Create and send SwipeEvent
        AttendanceEvent attendanceEvent = new AttendanceEvent();
        attendanceEvent.setEmployeeName("nikhil");
        attendanceEvent.setEmployeeId( employeeId);
        attendanceEvent.setTimestamp(LocalDateTime.now());
        attendanceEvent.setEventType(EventType.SWIPE_IN);
        attendanceEventRepository.save(attendanceEvent);
        //kafkaProducer.sendSwipeEvent(attendanceEvent);
        return ResponseEntity.ok("Event recorded successfully");
    }

    @PostMapping("/out/{employeeId}")
    public ResponseEntity<?> swipeOut(@PathVariable Long employeeId) {
        // Create and send SwipeEvent
        AttendanceEvent attendanceEvent = new AttendanceEvent();
        attendanceEvent.setEmployeeName("nikhil");
        attendanceEvent.setEmployeeId( employeeId);
        attendanceEvent.setTimestamp(LocalDateTime.now());
        attendanceEvent.setEventType(EventType.SWIPE_OUT);
        attendanceEventRepository.save(attendanceEvent);
        return ResponseEntity.ok("Event recorded successfully");
    }
    
}
