package ua.edu.sumdu.j2se.savchenko.tasks;

public class ArrayTaskList {
    private int size;
    private Task[] list = new Task[10];

    public void add(Task task) {
        checkSize(size);
        list[size] = task;
        size++;
    }

    private void checkSize(int futureSize) {
        if (futureSize == list.length) {
            increaseSize();
        }
    }

    private void increaseSize() {
        Task[] listCopy = new Task[size + 5];
        System.arraycopy(list, 0, listCopy,0, size);
        list = listCopy;
    }

    public boolean remove(Task task) {
        if (task != null) {
            for(int index = 0; index < size; index++) {
                if(task.equals(this.list[index])) {
                    int moved = size - index - 1;
                    if (moved > 0) {
                        System.arraycopy(list, index+1, list, index,
                                moved);
                    }
                    list[--size] = null;
                    return true;
                }
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public Task getTask(int index) {
        return list[index];
    }

    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList incoming = new ArrayTaskList();
        for(Task el : list) {
            if(el != null ){
                if(el.isActive() && el.isRepeated()) {
                    if (el.getStartTime() >= from && el.getEndTime() <= to) {
                        incoming.add(el);
                    }
                }
            }
        }
        return incoming;
    }
}
