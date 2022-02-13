package ua.edu.sumdu.j2se.savchenko.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.savchenko.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.savchenko.tasks.model.Task;
import ua.edu.sumdu.j2se.savchenko.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.savchenko.tasks.model.TaskModel;
import ua.edu.sumdu.j2se.savchenko.tasks.view.TaskListView;
import ua.edu.sumdu.j2se.savchenko.tasks.view.TaskView;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.SortedMap;

public class TaskController extends AbstractController {
    private TaskModel taskModel;
    private TaskListView listView;
    private TaskView taskView;

    private static final Logger logger = Logger.getLogger(TaskController.class);

    public TaskController(TaskModel taskModel, TaskListView listView, TaskView taskView) {
        this.taskModel = taskModel;
        this.listView = listView;
        this.taskView = taskView;
        this.taskModel.createFile();
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
        int taskToDeleteIndex = getTaskIndex();

        boolean deleted = taskModel.removeTask(taskToDeleteIndex);
        if (deleted) {
            listView.printMessage("Removed!");
        }
        listView.clearAllFields();
        printList();
        listView.setAction(new TaskActionListener());
    }

    private int getTaskIndex() {
        listView.setIndex();
        if (listView.getIndex() > taskModel.getTaskList().size()) {
            listView.printMessage("Index must be less or equal tasks amount");
            getTaskIndex();
        }
        return listView.getIndex();
    }

    private void editTask() {
        int taskIndex = getTaskIndex();

        Task taskForEdit = taskModel.findTask(taskIndex);
        taskView.print(taskForEdit);

        boolean save = selectAndSetProperty();
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

    private boolean selectAndSetProperty() {
        String selectedField = taskView.selectField();
        taskView.setNewValue(selectedField);
        boolean change = taskView.askQuestion("Change once more? (y/n)");
        if (change) {
            selectAndSetProperty();
        } else {
            return taskView.askQuestion("Save changes? (y/n)");
        }
        return false;
    }

    private void printCalendar() {
        LinkedTaskList taskList = taskModel.getTaskList();

        LocalDateTime from = listView.getStartOfPeriod();
        LocalDateTime to = listView.getEndOfPeriod();

        SortedMap<LocalDateTime, Set<Task>> calendar = null;

        try {
            calendar = taskModel.getCalendar(taskList, from, to);
        } catch (CloneNotSupportedException e) {
            logger.error(e.getMessage(), e);
        }

        if (calendar != null) {
            listView.printCalendar(calendar);
        }

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
        if (!fileToWrite.exists()) {
            fileToWrite = taskModel.createFile();
        }
        TaskIO.writeText(taskModel.getTaskList(), fileToWrite);
    }

    private void readFile() {
        File fileToRead = new File("taskList.json");
        if (!fileToRead.exists()) {
            taskModel.createFile();
        }

        if(fileToRead.length() == 0) {
            listView.printMessage("Your task list is empty now. Add new tasks...");
        } else {
            TaskIO.readText(taskModel.getTaskList(), fileToRead);
        }
    }

    private void finishWork() {
        writeTaskList();
        System.exit(0);
    }

    @Override
    public void startController() {
        readFile();
        this.listView.setAction(new TaskActionListener());
    }

    public class TaskActionListener extends ActionListener {
        @Override
        public void actionPerformed(String event) {
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

