package ua.edu.sumdu.j2se.savchenko.tasks;


import ua.edu.sumdu.j2se.savchenko.tasks.controller.NotificationController;
import ua.edu.sumdu.j2se.savchenko.tasks.controller.TaskController;
import ua.edu.sumdu.j2se.savchenko.tasks.model.NotificationModel;
import ua.edu.sumdu.j2se.savchenko.tasks.model.TaskModel;
import ua.edu.sumdu.j2se.savchenko.tasks.view.NotificationView;
import ua.edu.sumdu.j2se.savchenko.tasks.view.TaskListView;
import ua.edu.sumdu.j2se.savchenko.tasks.view.TaskView;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		TaskListView listView = new TaskListView();
		TaskView taskView = new TaskView();
		TaskModel taskModel = new TaskModel();
		NotificationView notificationView = new NotificationView();
		NotificationModel notificationModel = new NotificationModel();

		TaskThread threadForUser = new TaskThread(new TaskController(taskModel, listView, taskView));
		TaskThread threadForNotify = new TaskThread(new NotificationController(notificationModel, notificationView, taskModel));

		threadForUser.start();
		threadForUser.join(500);
		threadForNotify.start();
		threadForNotify.join();
	}
}
