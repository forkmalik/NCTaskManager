package ua.edu.sumdu.j2se.savchenko.tasks.view;

import ua.edu.sumdu.j2se.savchenko.tasks.model.Task;

public class NotificationView extends TaskListView{

    public void printNotification(Task task) {
        printMessage("Task\n");
        if (task.isRepeated()) {
            printRepeatableTask(task);
            printMessage("starts in 5 minutes");
        } else {
            printTask(task);
            printMessage("should be completed in 5 minutes");
        }
    }

}
