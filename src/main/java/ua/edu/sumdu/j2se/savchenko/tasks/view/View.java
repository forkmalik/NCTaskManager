package ua.edu.sumdu.j2se.savchenko.tasks.view;

import ua.edu.sumdu.j2se.savchenko.tasks.controller.ActionListener;
import ua.edu.sumdu.j2se.savchenko.tasks.model.AbstractTaskList;

public interface View {
    void print(AbstractTaskList taskList);
    void printMessage(String message);
}
