package ua.edu.sumdu.j2se.savchenko.tasks.view;

import ua.edu.sumdu.j2se.savchenko.tasks.model.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskView extends TaskListView{

    public void print(Task task) {
        System.out.println(task);
    }

    public boolean askQuestion(String question) {
        printMessage(question);
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        return answer.equals("y") || answer.equals("yes");
    }

    public String selectField() {
        printMessage("Enter a field name you want to change, please...");
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    public void setNewValue(String fieldName) {
        switch (fieldName) {
            case "title":
                setTitle();
                break;
            case "time":
                setTime();
                break;
            case "start":
                setStart();
                break;
            case "end":
                setEnd();
                break;
            case "interval":
                setInterval();
                break;
            case "active":
                setActive();
                break;
        }
    }
}
