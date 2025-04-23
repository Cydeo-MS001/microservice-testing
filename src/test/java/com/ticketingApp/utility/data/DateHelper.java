package com.ticketingApp.utility.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateHelper {
    public static Logger LOG = LogManager.getLogger();

    public static boolean isEndDateAfterStartDate(String endDate, String startDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        LOG.info("endDate {} startDate {} difference {}", end, start, ChronoUnit.DAYS.between(start, end));
        return end.isAfter(start);
    }

    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
