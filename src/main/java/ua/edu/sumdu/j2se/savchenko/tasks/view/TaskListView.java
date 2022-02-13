package ua.edu.sumdu.j2se.savchenko.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.savchenko.tasks.controller.ActionListener;
import ua.edu.sumdu.j2se.savchenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.savchenko.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;

public class TaskListView implements View {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;
    private int index;

    private static final Logger logger = Logger.getLogger(TaskListView.class);


    public void setFieldsValue() {
        setTitle();
        boolean repeatable = isRepeatable();
        if (repeatable) {
            setStart();
            setEnd();
            setInterval();
        } else {
            setTime();
        }
        setActive();
    }

    public void setActive() {
        System.out.println(ConsoleColors.ANSI_PURPLE + "Is this task active? (y/n)" + ConsoleColors.ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        String active = scanner.nextLine();

        this.active = active.equals("y") || active.equals("yes");

    }

    public void setTitle() {
        Scanner scanner = new Scanner(System.in);
        printMessage("Enter task title...");

        String title = scanner.nextLine();

        if(title.equals("")) {
            printMessage("The task title should not be blank ");
            setTitle();
        }

        this.title = title;
    }

    public void setTime() {
        Scanner scanner = new Scanner(System.in);

        LocalDateTimeParser parser = new LocalDateTimeParser();

        printMessage("Enter task date (dd.MM.yyyy)...");
        String dateString = scanner.nextLine();

        printMessage("Enter task time (HH:mm)...");
        String timeString = scanner.nextLine();
        LocalDateTime timeToSet = parser.parseString(dateString, timeString);
        if(timeToSet != null) {
            this.time = parser.parseString(dateString, timeString);
        } else {
            setTime();
        }

    }

    public void setStart() {
        Scanner scanner = new Scanner(System.in);

        LocalDateTimeParser parser = new LocalDateTimeParser();

        printMessage("Enter task start date...");
        String dateString = scanner.nextLine();

        printMessage("Enter task start time...");
        String timeString = scanner.nextLine();
        LocalDateTime timeToSet = parser.parseString(dateString, timeString);
        if(timeToSet != null) {
            this.start = parser.parseString(dateString, timeString);
        } else {
            setStart();
        }
    }

    public void setEnd() {
        Scanner scanner = new Scanner(System.in);

        LocalDateTimeParser parser = new LocalDateTimeParser();

        printMessage("Enter task end date...");
        String dateString = scanner.nextLine();

        printMessage("Enter task end time...");
        String timeString = scanner.nextLine();

        LocalDateTime timeToSet = parser.parseString(dateString, timeString);
        if(timeToSet != null) {
            this.end = parser.parseString(dateString, timeString);
        } else {
            setEnd();
        }
    }

    public void setInterval() {
        Scanner scanner = new Scanner(System.in);
        printMessage("Enter repeat interval (seconds)...");
        this.interval = scanner.nextInt();
    }

    public void setIndex() {
        Scanner scanner = new Scanner(System.in);
        printMessage("Enter task index...");
        this.index = Integer.parseInt(scanner.nextLine()) - 1;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getInterval() {
        return interval;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void print(AbstractTaskList taskList) {
        try {
            for (Task task : taskList) {
                if(task.isRepeated()) {
                    printRepeatableTask(task);
                } else {
                    printTask(task);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    public  void setAction(ActionListener actionListener) {
        if (actionListener == null) {
            throw new IllegalArgumentException("Action listener does not set");
        }

        Scanner scanner = new Scanner((System.in));
        printCommandList();
        String action = scanner.nextLine();
        try {
            actionListener.actionPerformed(action);
        } catch (CloneNotSupportedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.println(ConsoleColors.ANSI_PURPLE + message + ConsoleColors.ANSI_RESET);
    }

    public void printTask(Task task) {
        System.out.println(ConsoleColors.ANSI_YELLOW + "--------------------------------------\n"
                + task.getTitle() + "\n" + " time: " + task.getTime()
                + " active: " + task.isActive() + "\n"
                + "--------------------------------------\n" + ConsoleColors.ANSI_RESET);
    }

    public void printRepeatableTask(Task task) {
        System.out.println(ConsoleColors.ANSI_YELLOW + "--------------------------------------\n"
                + task.getTitle() + "\n"
                + " start time: " + task.getStartTime()
                + " end time: " + task.getEndTime() + "\n"
                + "repeat interval: " + task.getRepeatInterval()
                + " active: " + task.isActive() + "\n"
                + "--------------------------------------\n" + ConsoleColors.ANSI_RESET);
    }

    private boolean isRepeatable() {
        System.out.println(ConsoleColors.ANSI_PURPLE + "Is this task repeatable? (y/n)" + ConsoleColors.ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        return answer.equals("y") || answer.equals("yes");
    }

    private void printCommandList() {
        System.out.println("Select one of the suggested commands...\n");
        System.out.println(ConsoleColors.ANSI_BLUE + "add - to add a new task to list\n"
                        + "print - to print all tasks in list\n"
                        + "edit - to edit selected task\n"
                        + "remove - to remove selected from list\n"
                        + "calendar - to print calendar with tasks\n"
                        + "current - to print list of current tasks\n"
                        + "exit - to save and exit" + ConsoleColors.ANSI_RESET);
    }

    public void clearAllFields() {
        this.title = "";
        this.time = null;
        this.start = null;
        this.end = null;
        this.interval = 0;
        this.active = false;
    }

    public LocalDateTime getStartOfPeriod() {
        setStart();
        return getStart();
    }

    public LocalDateTime getEndOfPeriod() {
        setEnd();
        return getEnd();
    }

    public void printCalendar(SortedMap<LocalDateTime, Set<Task>> calendar) {
        Set<LocalDateTime> keys = calendar.keySet();

        for (LocalDateTime key: keys) {
            System.out.println(ConsoleColors.ANSI_YELLOW + key + "\n" + ConsoleColors.ANSI_RESET);
            Set<Task> tasks = calendar.get(key);
            for (Task task: tasks) {
                if (task.isRepeated()) {
                    printRepeatableTask(task);
                } else {
                    printTask(task);
                }
            }
        }
    }

}
