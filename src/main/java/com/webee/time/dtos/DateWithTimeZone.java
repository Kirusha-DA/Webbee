package com.webee.time.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DateWithTimeZone {
    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("timezone")
    private String timezone;
}
