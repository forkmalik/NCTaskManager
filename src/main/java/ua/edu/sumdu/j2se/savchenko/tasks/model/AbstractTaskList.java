package ua.edu.sumdu.j2se.savchenko.tasks.model;


import java.io.Serializable;
import java.util.Iterator;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {
    protected int size;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract Task getTask(int index);

    public int size() {
        return size;
    }

    @Override
    public abstract Iterator<Task> iterator();

    @Override
    public AbstractTaskList clone() throws CloneNotSupportedException {
        AbstractTaskList cloneList = null;
        try {
            cloneList = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Task task : this) {
            Task cloneTask = task.clone();
            if (cloneList != null) {
                cloneList.add(cloneTask);
            }
        }
        return  cloneList;
    }

    public abstract Stream<Task> getStream();

}
