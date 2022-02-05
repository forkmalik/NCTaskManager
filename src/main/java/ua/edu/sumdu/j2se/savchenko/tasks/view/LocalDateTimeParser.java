package ua.edu.sumdu.j2se.savchenko.tasks.view;

import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {
    private static final Logger logger = Logger.getLogger(LocalDateTimeParser.class);
    
    
    public LocalDateTime parseString(String date, String time) {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
                    .atDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.out.println("Wrong format of date or time. Try once more...");
            return null;
        }

    }

}
