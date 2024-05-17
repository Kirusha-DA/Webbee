package com.webee.time.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.webee.time.dtos.DateWithTimeZone;
import com.webee.time.services.ProductionCalendarService;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/production-callendar")
public class ProductionCalendarController {
    final private ProductionCalendarService productionCalendarService;

    ProductionCalendarController(ProductionCalendarService productionCalendarService) {
        this.productionCalendarService = productionCalendarService;
    }

    @GetMapping("/isweekend/2024/may")
    public boolean checkIfDateIsWeekend(@RequestParam Integer day) {
        return productionCalendarService.checkIfDateIsWeekend(day);
    }

    @PostMapping("/isfreetime")
    public boolean checkIfDateIsWeekendAndNotWorkingHours(
        @RequestBody DateWithTimeZone date
    ) {
        ZonedDateTime zonedDate = date.getDate().atZone(ZoneId.of(date.getTimezone()));
        return productionCalendarService.checkIfDateIsWeekendAndNotWorkingHoursInMoscowTZ(zonedDate);
    }
    
}