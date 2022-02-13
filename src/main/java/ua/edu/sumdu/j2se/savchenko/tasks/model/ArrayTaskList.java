package ua.edu.sumdu.j2se.savchenko.tasks.model;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList {
    private Task[] list = new Task[10];

    private static final Logger logger = Logger.getLogger(ArrayTaskList.class);

    public ArrayTaskList(){}

    @Override
     public void add(Task task) {
        if(task != null) {
            checkSize(size);
            list[size] = task;
            size++;
        } else {
            System.out.println("Task must not be null");
            Exception e = new NullPointerException();
            logger.fatal(e.getMessage(), e);
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
            System.out.println("The index must be less than or equal to the number of items");
            Exception e = new IndexOutOfBoundsException();
            logger.warn(e.getMessage(), e);
        }
        return list[index];
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {

            private int index = 0;
            private  int previousLoc = -1;

            @Override
            public boolean hasNext() {
                return index < size() && list[index] != null;
            }

            @Override
            public Task next() {
                try {
                    int i = index;
                    Task next = getTask(i);
                    previousLoc = i;
                    index = i + 1;
                    return next;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("The index must be less than or equal to the number of items");
                    logger.warn(e.getMessage(), e);
                }
                return null;
            }

            @Override
            public void remove() {
                if(previousLoc < 0) {
                    System.out.println("Nothing to remove");
                    Exception e = new IllegalStateException();
                    logger.warn(e.getMessage(), e);
                }

                try {
                    ArrayTaskList.this.remove(list[previousLoc]);
                    if (previousLoc < index)
                        index--;
                    previousLoc = -1;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("The index must be less than or equal to the number of items");
                    logger.warn(e.getMessage(), e);
                }
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayTaskList list1 = (ArrayTaskList) o;

        return Arrays.equals(list, list1.list);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(list);
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "list=" + Arrays.toString(list) +
                '}';
    }

    @Override
    public Stream<Task> getStream() {

        return Arrays.stream(this.list);
    }

}
