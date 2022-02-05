package ua.edu.sumdu.j2se.savchenko.tasks;

import ua.edu.sumdu.j2se.savchenko.tasks.controller.AbstractController;

public class TaskThread extends Thread{
    private AbstractController controller;

    public TaskThread(AbstractController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.startController();
    }

}
