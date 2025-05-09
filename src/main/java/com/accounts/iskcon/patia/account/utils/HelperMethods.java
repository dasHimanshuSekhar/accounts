package com.accounts.iskcon.patia.account.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class HelperMethods {

    /**
     * Acceptable formats: [yyyy-MM-dd HH:mm:ss or yyyy-MM-dd]
     * */
    public LocalDateTime parseDateTime(String input) {
        try {
            if (input.contains(" ")) {
                // Full date and time provided
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                return LocalDateTime.parse(input, formatter);
            } else {
                // Only date provided, add current time
                LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                return LocalDateTime.of(date, LocalTime.now());
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format! Expected: dd-MM-yyyy or dd-MM-yyyy HH:mm", e);
        }
    }
}
