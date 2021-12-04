package ua.edu.sumdu.j2se.savchenko.tasks;

public abstract class AbstractTaskList {
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        while (index != size()) {
            Task task = getTask(index);
            if(task != null) {
                if(task.nextTimeAfter(from) != -1) {
                    if (task.getStartTime() >= from && task.getEndTime() <= to) {
                        incoming.add(task);
                    }
                }
            }
            index++;
        }
        return incoming;
    }

}
