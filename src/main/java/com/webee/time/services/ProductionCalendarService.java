package com.webee.time.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
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

    public boolean checkIfDateIsWeekendAndNotWorkingHours(LocalDateTime date) {
        date.atZone(ZoneId.of("Europe/Moscow"));

        boolean isDateIsWorkingDayAndBetween9And18 = !checkIfDateIsWeekend(date) && checkIfDateIsBetween9And18Hours(date);

        if (!isDateIsWorkingDayAndBetween9And18 || checkIfDateIsHoliday(date)) {
            return true;
        } 

        return false;
    }

    private boolean checkIfDateIsHoliday(LocalDateTime date) {
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

    private boolean checkIfDateIsWeekend(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (weekends.contains(dayOfWeek)) {
            return true;
        }
        return false;
    }

    private boolean checkIfDateIsBetween9And18Hours(LocalDateTime date) {
        Integer hour = date.getHour();
        if (9 <= hour && hour <= 18) {
            return true;
        }
        return false;
    }
}
