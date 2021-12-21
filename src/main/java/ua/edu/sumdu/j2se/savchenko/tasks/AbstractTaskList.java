package ua.edu.sumdu.j2se.savchenko.tasks;


import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable{
    protected int size;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract Task getTask(int index);

    public int size() {
        return size;
    }

    public final AbstractTaskList incoming(int from, int to) {
        AbstractTaskList incoming = null;
        try {
            incoming = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


       this.getStream()
               .filter(Objects::nonNull)
                .filter(task -> task.getStartTime() >= from && task.getEndTime() <= to && task.nextTimeAfter(from) != -1)
                .forEach(incoming::add);

        return incoming;
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
