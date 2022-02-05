package ua.edu.sumdu.j2se.savchenko.tasks.controller;

import ua.edu.sumdu.j2se.savchenko.tasks.model.NotificationModel;
import ua.edu.sumdu.j2se.savchenko.tasks.model.Task;
import ua.edu.sumdu.j2se.savchenko.tasks.model.TaskModel;
import ua.edu.sumdu.j2se.savchenko.tasks.view.NotificationView;

import java.util.ArrayList;

public class NotificationController extends AbstractController{
    private NotificationModel notificationModel;
    private NotificationView notificationView;
    private TaskModel taskModel;

    public NotificationController(NotificationModel notificationModel, NotificationView notificationView, TaskModel taskModel) {
        this.notificationModel = notificationModel;
        this.notificationView = notificationView;
        this.taskModel = taskModel;
    }

   private void createNotification() {
       ArrayList<Task> printedTasks = new ArrayList<>();
        while (true) {
            Task taskToDo = notificationModel.getTaskToDo(taskModel.getTaskList());
            if (taskToDo != null && !printedTasks.contains(taskToDo)) {
                notificationView.printNotification(taskToDo);
                printedTasks.add(taskToDo);
            }
        }
   }

    @Override
    public void startController() {
        createNotification();
    }
}
