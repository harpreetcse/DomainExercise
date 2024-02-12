package com.domainexcercise.microservices.restful.eventstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.domainexcercise.microservices.restful.eventstore.model.AttendanceEvent;

@Service
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, AttendanceEvent> kafkaTemplate;

    public void sendSwipeEvent(AttendanceEvent attendanceEvent) {
        kafkaTemplate.send("swipe-events", attendanceEvent);
    }
}
