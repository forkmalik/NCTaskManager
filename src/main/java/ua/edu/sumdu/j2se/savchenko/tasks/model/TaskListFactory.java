package ua.edu.sumdu.j2se.savchenko.tasks.model;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        switch (type) {
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
            default:
                throw new IllegalArgumentException("You should input right list type");
        }
    }
}
