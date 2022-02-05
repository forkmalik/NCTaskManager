package ua.edu.sumdu.j2se.savchenko.tasks.model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class TaskModel implements Model {

    private LinkedTaskList taskList = new LinkedTaskList();


    public void addTask(Task task) {
        taskList.add(task);
    }

    public Task createSimpleTask(String title, LocalDateTime time, boolean active) {
        Task task = new Task(title, time);
        task.setActive(active);
        return task;
    }

    public Task createRepeatedTask(String title, LocalDateTime start, LocalDateTime end, int interval, boolean active) {
        Task task = new Task(title, start, end, interval);
        task.setActive(active);
        return task;
    }

    public Task createEmptyTask() {
        return new Task();
    }

    public LinkedTaskList getTaskList() {
        return taskList;
    }

    public void editTask(int taskForEditIndex, Task donorTask) {
        Task taskForEdit = findTask(taskForEditIndex);

        String title = donorTask.getTitle();
        LocalDateTime time = donorTask.getTime();
        LocalDateTime start = donorTask.getStartTime();
        LocalDateTime end = donorTask.getEndTime();
        int interval = donorTask.getRepeatInterval();
        boolean active = donorTask.isActive();

        if (title != null && !title.equals(taskForEdit.getTitle())) {
            taskForEdit.setTitle(title);
        }
        if (time != null && !time.equals(taskForEdit.getTime())) {
            taskForEdit.setTime(time);
        }
        if (start != null && !start.equals(taskForEdit.getStartTime())) {
            taskForEdit.setStartTime(start);
        }
        if (end != null && !end.equals(taskForEdit.getEndTime())) {
            taskForEdit.setEndTime(end);
        }
        if (interval != taskForEdit.getRepeatInterval()) {
            taskForEdit.setRepeatInterval(interval);
        }
        if (active != taskForEdit.isActive()) {
            taskForEdit.setActive(active);
        }
    }

    public boolean removeTask(int taskToDeleteIndex) {
        Task taskToDelete = findTask(taskToDeleteIndex);

        return taskList.remove(taskToDelete);
    }

    public Task findTask(int index) {
        return taskList.getTask(index);
    }

    public SortedMap<LocalDateTime, Set<Task>> getCalendar(Iterable<Task> tasks, LocalDateTime from, LocalDateTime to)
            throws CloneNotSupportedException {
        return Tasks.calendar(tasks, from, to);
    }

    public Iterable<Task> getIncoming(Iterable<Task> tasks,LocalDateTime from, LocalDateTime to) {
        return Tasks.incoming(tasks, from, to);
    }

    public File createFile() {
        File file = new File("taskList.json");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
