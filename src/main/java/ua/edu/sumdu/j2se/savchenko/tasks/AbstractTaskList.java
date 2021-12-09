package ua.edu.sumdu.j2se.savchenko.tasks;

import java.util.Iterator;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {
    protected int size;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract Task getTask(int index);

    public int size() {
        return size;
    }

    public AbstractTaskList incoming(int from, int to) {
        int index = 0;
        AbstractTaskList incoming = null;
        try {
            incoming = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        while (index != size()) {
            Task task = getTask(index);
            if(task != null) {
                if(task.nextTimeAfter(from) != -1) {
                    if (task.getStartTime() >= from && task.getEndTime() <= to) {
                        if (incoming != null) {
                            incoming.add(task);
                        }
                    }
                }
            }
            index++;
        }
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

}
