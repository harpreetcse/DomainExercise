package com.domainexcercise.microservices.restful.eventstore.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domainexcercise.microservices.restful.eventstore.model.AttendanceEvent;

public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent, Long> {

    List<AttendanceEvent> findByEmployeeIdAndTimestampBetween(Long employeeId, LocalDateTime atStartOfDay,LocalDateTime atTime);

    List<AttendanceEvent> findAllEventsByTimestampBetween(LocalDateTime atStartOfDay, LocalDateTime atTime);
    
}
