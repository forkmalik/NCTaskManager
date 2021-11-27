package ua.edu.sumdu.j2se.savchenko.tasks;

public abstract class AbstractTaskList {
    protected int size;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract Task getTask(int index);

    public int size() {
        return size;
    }

    public abstract AbstractTaskList incoming(int from, int to);

}
