package com.webee.time.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.webee.time.services.ProductionCalendarService;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/production-callendar")
public class ProductionCalendarController {
    final private ProductionCalendarService productionCalendarService;

    ProductionCalendarController(ProductionCalendarService productionCalendarService) {
        this.productionCalendarService = productionCalendarService;
    }

    @GetMapping("/isweekend")
    public boolean checkIfDateIsWeekend(@RequestParam Integer day) {
        return productionCalendarService.checkIfDateIsWeekend(day);
    }

    @GetMapping("/isfreetime")
    public boolean checkIfDateIsWeekendAndNotWorkingHours(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        return productionCalendarService.checkIfDateIsWeekendAndNotWorkingHours(date);
    }
    
}