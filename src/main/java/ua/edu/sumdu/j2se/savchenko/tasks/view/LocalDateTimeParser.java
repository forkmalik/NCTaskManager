package ua.edu.sumdu.j2se.savchenko.tasks.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {

    public LocalDateTime parseString(String date, String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
                .atDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

}
