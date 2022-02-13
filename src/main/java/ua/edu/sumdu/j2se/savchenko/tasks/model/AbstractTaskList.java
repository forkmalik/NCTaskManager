package ua.edu.sumdu.j2se.savchenko.tasks.model;


import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Iterator;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {
    private static final Logger logger = Logger.getLogger(AbstractTaskList.class);


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
            logger.error(e.getMessage(), e);
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
