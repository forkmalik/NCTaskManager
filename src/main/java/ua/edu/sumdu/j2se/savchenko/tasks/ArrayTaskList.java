package ua.edu.sumdu.j2se.savchenko.tasks;

public class ArrayTaskList extends AbstractTaskList {
    private Task[] list = new Task[10];

    public ArrayTaskList(){}

    @Override
    public void add(Task task) {
        if(task != null) {
            checkSize(size);
            list[size] = task;
            size++;
        } else {
          throw new NullPointerException("Task must not be null");
        }

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

    @Override
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

    @Override
    public Task getTask(int index) {
        if(index > list.length){
            throw new IndexOutOfBoundsException("Index must be less or equal to elements amount");
        }
        return list[index];
    }
}
