package com.vega.credit.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DateTimeUtil {

    /* Returns current Date in UTC in ISO format */
    public String getCurrentDate() {
        return LocalDate.now().toString();
    }

    /* Returns current timestamp for UTC */
    public String getCurrentTimeStamp() {
        return Long.toString(Instant.now().toEpochMilli());
    }

    public boolean isValidDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            dateTimeFormatter.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
