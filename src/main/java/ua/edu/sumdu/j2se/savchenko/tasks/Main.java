package ua.edu.sumdu.j2se.savchenko.tasks;


import ua.edu.sumdu.j2se.savchenko.tasks.controller.TaskController;
import ua.edu.sumdu.j2se.savchenko.tasks.model.TaskModel;
import ua.edu.sumdu.j2se.savchenko.tasks.view.CalendarView;
import ua.edu.sumdu.j2se.savchenko.tasks.view.TaskListView;
import ua.edu.sumdu.j2se.savchenko.tasks.view.TaskView;

public class Main {

	public static void main(String[] args) {
		TaskListView listView = new TaskListView();
		CalendarView calendarView = new CalendarView();
		TaskView taskView = new TaskView();
		TaskModel taskModel = new TaskModel();
		TaskController taskController = new TaskController(taskModel, listView, calendarView, taskView);
	}
}
