package com.webee.time.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class ProductionCalendarService {
    final private Set<DayOfWeek> weekends = new HashSet<>(Arrays.asList(
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY
    ));


    public boolean checkIfDateIsWeekend(Integer day) {
        Integer currentYear = 2024;
        Month currentMonth = Month.MAY;
        Set<LocalDate> weekendDaysDuringMay2024 = new HashSet<>(Arrays.asList(
            LocalDate.of(currentYear, currentMonth, 1),
            LocalDate.of(currentYear, currentMonth, 4),
            LocalDate.of(currentYear, currentMonth, 5),
            LocalDate.of(currentYear, currentMonth, 9),
            LocalDate.of(currentYear, currentMonth, 10),
            LocalDate.of(currentYear, currentMonth, 11),
            LocalDate.of(currentYear, currentMonth, 12),
            LocalDate.of(currentYear, currentMonth, 18),
            LocalDate.of(currentYear, currentMonth, 19),
            LocalDate.of(currentYear, currentMonth, 25),
            LocalDate.of(currentYear, currentMonth, 26)
        ));

        LocalDate date = LocalDate.of(currentYear, currentMonth, day);
        return weekendDaysDuringMay2024.contains(date);
    }

    public boolean checkIfDateIsWeekendAndNotWorkingHoursInMoscowTZ(ZonedDateTime zonedDateTime) {
        ZonedDateTime dateWithMoscowTZ = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
      
        log.info(dateWithMoscowTZ);
        
        boolean isDateIsWorkingDayAndBetween9And18 = !checkIfDateIsWeekend(dateWithMoscowTZ) && checkIfDateIsBetween9And18Hours(dateWithMoscowTZ);

        if (!isDateIsWorkingDayAndBetween9And18 || checkIfDateIsHoliday(dateWithMoscowTZ)) {
            return true;
        } 

        return false;
    }

    private boolean checkIfDateIsHoliday(ZonedDateTime date) {
        Integer dayOfMonth = date.getDayOfMonth();
        switch (date.getMonth()) {
            case JANUARY:
                return checkIfDateIsHolidayDuringJanuary(dayOfMonth);
            case FEBRUARY:
                return checkIfDateIsHolidayDuringFebruary(dayOfMonth);
            case MARCH:
                return checkIfDateIsHolidayDuringMarch(dayOfMonth);
            case APRIL:
                return checkIfDateIsHolidayDuringApril(dayOfMonth);
            case MAY:
                return checkIfDateIsHolidayDuringMay(dayOfMonth);
            case JUNE:
                return checkIfDateIsHolidayDuringJune(dayOfMonth);
            case NOVEMBER:
                return checkIfDateIsHolidayDuringNovember(dayOfMonth);
            case DECEMBER:
                return checkIfDateIsHolidayDuringDecember(dayOfMonth);
            default:
                return false;
        }
    }

    private boolean checkIfDateIsHolidayDuringJanuary(Integer dayOfMonth) {
        Set<Integer> currentMonthHolidays = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 8));
        return checkIfHolidaysContainDay(currentMonthHolidays, dayOfMonth);
    }

    private boolean checkIfDateIsHolidayDuringFebruary(Integer dayOfMonth) {
        Set<Integer> currentMonthHolidays = new HashSet<>(Arrays.asList(23));
        return checkIfHolidaysContainDay(currentMonthHolidays, dayOfMonth);
    }

    private boolean checkIfDateIsHolidayDuringMarch(Integer dayOfMonth) {
        Set<Integer> currentMonthHolidays = new HashSet<>(Arrays.asList(8));
        return checkIfHolidaysContainDay(currentMonthHolidays, dayOfMonth);
    }

    private boolean checkIfDateIsHolidayDuringApril(Integer dayOfMonth) {
        Set<Integer> currentMonthHolidays = new HashSet<>(Arrays.asList(29, 30));
        return checkIfHolidaysContainDay(currentMonthHolidays, dayOfMonth);
    }

    private boolean checkIfDateIsHolidayDuringMay(Integer dayOfMonth) {
        Set<Integer> currentMonthHolidays = new HashSet<>(Arrays.asList(1, 9, 10));
        return checkIfHolidaysContainDay(currentMonthHolidays, dayOfMonth);
    }

    private boolean checkIfDateIsHolidayDuringJune(Integer dayOfMonth) {
        Set<Integer> currentMonthHolidays = new HashSet<>(Arrays.asList(12));
        return checkIfHolidaysContainDay(currentMonthHolidays, dayOfMonth);
    }

    private boolean checkIfDateIsHolidayDuringNovember(Integer dayOfMonth) {
        Set<Integer> currentMonthHolidays = new HashSet<>(Arrays.asList(4));
        return checkIfHolidaysContainDay(currentMonthHolidays, dayOfMonth);
    }

    private boolean checkIfDateIsHolidayDuringDecember(Integer dayOfMonth) {
        Set<Integer> currentMonthHolidays = new HashSet<>(Arrays.asList(30, 31));
        return checkIfHolidaysContainDay(currentMonthHolidays, dayOfMonth);
    }

    private boolean checkIfHolidaysContainDay(Set<Integer> holidays,Integer dayOfMonth) {
        if (holidays.contains(dayOfMonth)) {
            return true;
        }
        return false;
    }

    private boolean checkIfDateIsWeekend(ZonedDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (weekends.contains(dayOfWeek)) {
            return true;
        }
        return false;
    }

    private boolean checkIfDateIsBetween9And18Hours(ZonedDateTime zonedDate) {
        LocalDateTime date9Hours = LocalDateTime.of(zonedDate.getYear(), zonedDate.getMonth(), zonedDate.getDayOfMonth(), 9, 0, 1);
        LocalDateTime date18Hours = LocalDateTime.of(zonedDate.getYear(), zonedDate.getMonth(), zonedDate.getDayOfMonth(), 18, 0, 1);
        LocalDateTime date = zonedDate.toLocalDateTime();

        if (date.isAfter(date9Hours) && date.isBefore(date18Hours)) {
            return true;
        }
        return false;
    }
}
