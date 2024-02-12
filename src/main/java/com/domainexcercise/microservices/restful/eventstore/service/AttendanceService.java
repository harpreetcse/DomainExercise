package com.domainexcercise.microservices.restful.eventstore.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domainexcercise.microservices.restful.eventstore.model.AttendanceEvent;
import com.domainexcercise.microservices.restful.eventstore.model.EventType;
import com.domainexcercise.microservices.restful.eventstore.repository.AttendanceEventRepository;


@Service
public class AttendanceService {
    @Autowired
    private AttendanceEventRepository eventRepository;

    public Duration calculateTotalHoursByEmployeeID(Long employeeId, LocalDate date) {
        // Retrieve events for the given employee and date
        List<AttendanceEvent> events = eventRepository.findByEmployeeIdAndTimestampBetween(
            employeeId,
            date.atStartOfDay(),
            date.atTime(LocalTime.MAX)
        );
        Duration duration = null;
        return duration;
    }

    public Duration calculateTotalHours(List<AttendanceEvent> attendanceEvents) {
        
        // Calculate total hours based on swipe-in and swipe-out timestamps
        // ...
        LocalDateTime firstSwipeIn = null;
        LocalDateTime lastSwipeOut = null;
        for (AttendanceEvent event : attendanceEvents) {
            if (event.getEventType() == EventType.SWIPE_IN) {
                if (firstSwipeIn == null || event.getTimestamp().isBefore(firstSwipeIn)) {
                    firstSwipeIn = event.getTimestamp();
                }
            } else if (event.getEventType() == EventType.SWIPE_OUT) {
                if (lastSwipeOut == null || event.getTimestamp().isAfter(lastSwipeOut)) {
                    lastSwipeOut = event.getTimestamp();
                }
            }
        }

        // If both first swipe-in and last swipe-out are found, calculate duration
        if (firstSwipeIn != null && lastSwipeOut != null) {
            return Duration.between(firstSwipeIn, lastSwipeOut);
        } else {
            return Duration.ZERO; // Return zero duration if one of the events is missing
        }
    }
}
