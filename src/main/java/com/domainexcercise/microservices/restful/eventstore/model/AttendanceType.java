package com.domainexcercise.microservices.restful.eventstore.model;

public enum AttendanceType{
    ABSENT ("Absent"),
    HALF_DAY("Half Day"),
    PRESENT("Present");

    private final String value;

    AttendanceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}