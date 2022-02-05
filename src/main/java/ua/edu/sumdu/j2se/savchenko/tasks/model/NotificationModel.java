package ua.edu.sumdu.j2se.savchenko.tasks.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class NotificationModel {
    public Task getTaskToDo(AbstractTaskList taskList) {
        LocalDateTime time;

        LocalDateTime NOW_PLUS_FIVE = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(5);
        for (Task task : taskList) {
            if (task.isRepeated()) {
                time = task.getStartTime();
            } else {
                time = task.getTime();
            }
            if (NOW_PLUS_FIVE.equals(time)) {
                return task;
            }
        }
        return null;
    }
}
