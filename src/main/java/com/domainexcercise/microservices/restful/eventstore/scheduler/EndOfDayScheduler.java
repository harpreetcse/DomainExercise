package com.domainexcercise.microservices.restful.eventstore.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domainexcercise.microservices.restful.eventstore.model.AttendanceEvent;
import com.domainexcercise.microservices.restful.eventstore.repository.AttendanceEventRepository;
import com.domainexcercise.microservices.restful.eventstore.service.AttendanceService;
import com.domainexcercise.microservices.restful.eventstore.service.KafkaProducer;

import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class EndOfDayScheduler {

    private final AttendanceEventRepository attendanceEventRepository;
    private final AttendanceService attendanceService;
    private final KafkaProducer kafkaProducer;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public EndOfDayScheduler(AttendanceEventRepository attendanceEventRepository, AttendanceService attendanceService, KafkaProducer kafkaProducer) {
        this.attendanceEventRepository = attendanceEventRepository;
        this.attendanceService = attendanceService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostConstruct
    public void scheduleEndOfDayTask() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfDay = now.with(LocalTime.MAX); // End of the current day

        Duration durationUntilEndOfDay = Duration.between(now, endOfDay);
        long initialDelaySeconds = durationUntilEndOfDay.getSeconds();

        scheduler.schedule(this::executeAtEndOfDay, initialDelaySeconds, TimeUnit.SECONDS);
    }

    private void executeAtEndOfDay() {
        // Retrieve attendance events for the day from the repository
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        List<AttendanceEvent> attendanceEvents = attendanceEventRepository.findAllEventsByTimestampBetween(startOfDay, endOfDay);

        Map<Long, List<AttendanceEvent>> attendanceEventsMap = attendanceEvents.stream()
                .collect(Collectors.groupingBy(AttendanceEvent::getEmployeeId,
                        Collectors.toList()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(Comparator.comparing(AttendanceEvent::getTimestamp))
                                .collect(Collectors.toList())));
        
        // Call the attendance calculator method
        for (Map.Entry<Long, List<AttendanceEvent>> entry : attendanceEventsMap.entrySet()) {
            Long employeeId = entry.getKey();
            Duration totalHoursInOffice = attendanceService.calculateTotalHours(entry.getValue());
            System.out.println("Total hours in office today: " + totalHoursInOffice.toHours() + " hours");
        }
        
        
        
        // You can do whatever you want with the total hours, such as logging or saving to the database

    }
}

