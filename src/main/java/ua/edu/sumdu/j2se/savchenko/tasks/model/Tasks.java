package ua.edu.sumdu.j2se.savchenko.tasks.model;

import ua.edu.sumdu.j2se.savchenko.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class Tasks {
    private static boolean checkInterval (Task task, LocalDateTime from, LocalDateTime to) {
        if(task.isRepeated()) {
            LocalDateTime next = task.nextTimeAfter(from);
            if (next != null) {
                return !next.isAfter(to);
            } else {
                return false;
            }
        }
        return true;
    }

    public static Iterable<Task> incoming (Iterable<Task> tasks,LocalDateTime from, LocalDateTime to) {
        LinkedTaskList incoming = new LinkedTaskList();

        Stream<Task> stream = StreamSupport.stream(tasks.spliterator(), false);

        stream.filter(Objects::nonNull).filter(task -> task.nextTimeAfter(from) != null && checkInterval(task, from, to)).forEach(incoming::add);


        return incoming;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar (Iterable<Task> tasks, LocalDateTime from, LocalDateTime to)
            throws CloneNotSupportedException {

        if(tasks == null || from == null || to == null) {
            throw new IllegalArgumentException("Arguments is empty!");
        }

        SortedMap<LocalDateTime, Set<Task>> taskCalendar = new TreeMap<>();

        Iterable<Task> incoming = incoming(tasks, from, to);

        for (Task task : incoming) {
            LocalDateTime next = task.nextTimeAfter(from);
            if (task.isRepeated()) {
                while (!next.isAfter(to)) {
                    taskCalendar.putIfAbsent(next, new HashSet<>());
                    taskCalendar.get(next).add(task);
                    next = next.plusSeconds(task.getRepeatInterval());
                }
            } else {
                taskCalendar.putIfAbsent(next, new HashSet<>());
                taskCalendar.get(next).add(task.clone());
            }
        }
       return taskCalendar;
    }
}


