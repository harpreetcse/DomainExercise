package com.domainexcercise.microservices.restful.eventstore.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domainexcercise.microservices.restful.eventstore.model.AttendanceEvent;
import com.domainexcercise.microservices.restful.eventstore.repository.AttendanceEventRepository;


@Service
public class AttendanceService {
    @Autowired
    private AttendanceEventRepository eventRepository;

    public Duration calculateTotalHours(Long employeeId, LocalDate date) {
        // Retrieve events for the given employee and date
        List<AttendanceEvent> events = eventRepository.findByEmployeeIdAndTimestampBetween(
            employeeId,
            date.atStartOfDay(),
            date.atTime(LocalTime.MAX)
        );

        // Calculate total hours based on swipe-in and swipe-out timestamps
        // ...

        Duration totalHours = null;
        return totalHours;
    }
}
