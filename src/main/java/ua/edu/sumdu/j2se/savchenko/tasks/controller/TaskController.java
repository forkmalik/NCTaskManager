package ua.edu.sumdu.j2se.savchenko.tasks.controller;

import ua.edu.sumdu.j2se.savchenko.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.savchenko.tasks.model.Task;
import ua.edu.sumdu.j2se.savchenko.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.savchenko.tasks.model.TaskModel;
import ua.edu.sumdu.j2se.savchenko.tasks.view.CalendarView;
import ua.edu.sumdu.j2se.savchenko.tasks.view.TaskListView;
import ua.edu.sumdu.j2se.savchenko.tasks.view.TaskView;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedMap;

public class TaskController {
    private TaskModel taskModel;
    private TaskListView listView;
    private CalendarView calendarView;
    private TaskView taskView;

    public TaskController(TaskModel taskModel, TaskListView listView, CalendarView calendarView, TaskView taskView) {
        this.taskModel = taskModel;
        this.listView = listView;
        this.calendarView = calendarView;
        this.taskView = taskView;
        readFile();
        this.listView.setAction(new TaskActionListener());

    }

    private void addTask() {
        Task task;

        listView.setFieldsValue();

        String title = listView.getTitle();
        int interval = listView.getInterval();
        boolean active = listView.isActive();
        if (interval > 0) {
            LocalDateTime start = listView.getStart();
            LocalDateTime end = listView.getEnd();
            task = taskModel.createRepeatedTask(title, start, end, interval, active);
        } else {
            LocalDateTime time = listView.getTime();
            task = taskModel.createSimpleTask(title, time, active);
        }

        taskModel.addTask(task);
        listView.clearAllFields();
        printList();
        listView.setAction(new TaskActionListener());
    }

    private void printList() {
        LinkedTaskList taskList = taskModel.getTaskList();
        listView.print(taskList);
        listView.setAction(new TaskActionListener());
    }

    private void removeTask() {
        listView.setIndex();
        int taskToDeleteIndex = listView.getIndex();
        boolean deleted = taskModel.removeTask(taskToDeleteIndex);
        if (deleted) {
            listView.printMessage("Removed!");
        }
        listView.clearAllFields();
        printList();
        listView.setAction(new TaskActionListener());
    }

    private void editTask() {
        listView.setIndex();
        Task taskForEdit = taskModel.findTask(listView.getIndex());
        taskView.print(taskForEdit);

        boolean save = selectAndSet();
        if (save) {
            saveChanges();
        }
        taskView.clearAllFields();
        printList();
        listView.setAction(new TaskActionListener());
    }

    private void saveChanges() {
        int taskToEditIndex = listView.getIndex();
        Task donorTask = taskModel.createEmptyTask();

        donorTask.setTitle(taskView.getTitle());
        donorTask.setTime(taskView.getTime());
        donorTask.setStartTime(taskView.getStart());
        donorTask.setEndTime(taskView.getEnd());
        donorTask.setRepeatInterval(taskView.getInterval());
        donorTask.setActive(taskView.isActive());

        taskModel.editTask(taskToEditIndex, donorTask);

    }

    private boolean selectAndSet() {
        String selectedField = taskView.selectField();
        taskView.setNewValue(selectedField);
        boolean change = taskView.askQuestion("Change once more? (y/n)");
        if(change) {
            selectAndSet();
        } else {
            boolean save = taskView.askQuestion("Save changes? (y/n)");
            return save;
        }
        return false;
    }

    private void printCalendar() throws CloneNotSupportedException {
        LinkedTaskList taskList = taskModel.getTaskList();

        LocalDateTime from = listView.getStartOfPeriod();
        LocalDateTime to = listView.getEndOfPeriod();

        SortedMap<LocalDateTime, Set<Task>> calendar = taskModel.getCalendar(taskList, from, to);

        listView.printCalendar(calendar);

        listView.clearAllFields();
        listView.setAction(new TaskActionListener());

    }

    private void printCurrent() {
        LinkedTaskList taskList = taskModel.getTaskList();

        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now().toLocalDate().atTime(LocalTime.MAX);

        LinkedTaskList incoming = (LinkedTaskList) taskModel.getIncoming(taskList, from, to);

        listView.print(incoming);

        listView.clearAllFields();
        listView.setAction(new TaskActionListener());
    }

    private void writeTaskList() {
        File fileToWrite = new File("taskList.json");
        if(!fileToWrite.exists()) {
            fileToWrite = taskModel.createFile();
        }
        TaskIO.writeText(taskModel.getTaskList(), fileToWrite);
    }

    private void readFile() {
        File fileToRead = new File("taskList.json");
        if(!fileToRead.exists()) {
            listView.printMessage("File not found. Please create and write a new task list");
        } else {
            TaskIO.readText(taskModel.getTaskList(), fileToRead);
            if(taskModel.getTaskList().size() == 0) {
                listView.printMessage("File is empty");
            } else {
                listView.printMessage("File read");
            }

        }
    }

    private void finishWork() {
        writeTaskList();
        System.exit(0);
    }

    public class TaskActionListener extends ActionListener {
        @Override
        public void actionPerformed(String event) throws CloneNotSupportedException {
                switch (event) {
                    case "print":
                        printList();
                        break;
                    case "add":
                        addTask();
                        break;
                    case "remove":
                        removeTask();
                        break;
                    case "edit":
                        editTask();
                    case "calendar":
                        printCalendar();
                    case "current":
                        printCurrent();
                    case "exit":
                        finishWork();
                    default:
                        readFile();
                        listView.setAction(new TaskActionListener());
                }
        }
    }
}

