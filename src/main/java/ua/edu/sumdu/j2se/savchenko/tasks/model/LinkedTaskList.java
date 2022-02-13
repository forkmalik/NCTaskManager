package ua.edu.sumdu.j2se.savchenko.tasks.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList {

    private class Node {
        private Task task;
        private Node next;

        Node (Task task) {
            this.task = task;
            this.next = null;
        }
        Node () {}
    }

    private Node head;

    public LinkedTaskList(){}

    @Override
    public void add(Task task) {
        if(task != null) {
            Node newNode = new Node(task);
            if (head == null) {
                head = newNode;
            } else {
            Node lastNode = head;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            lastNode.next = newNode;
            }
            size++;
        } else {
            System.out.println("Task must not be null");
            Exception e = new NullPointerException();
            logger.fatal(e.getMessage(), e);
        }

    }

    @Override
    public boolean remove(Task task) {
        if (task != null) {
            if(head.task.equals(task)) {
                head = head.next;
                size--;
                return true;
            }
           Node nodeToDel = head;
           while (nodeToDel.next != null) {
               if (nodeToDel.next.task.equals(task)) {
                   nodeToDel.next = nodeToDel.next.next;
                   size--;
                   return true;
               }
               nodeToDel = nodeToDel.next;
           }
        }
        return false;
    }


    @Override
    public Task getTask(int index) {
        if(index > size){
            Exception e = new IndexOutOfBoundsException("Index must be less or equal to elements amount");
            System.out.println("The index must be less than or equal to the number of items");
            logger.warn(e.getMessage(), e);
        }
        if(index == 0) {
            return head.task;
        } else {
          Node node = head;
          for (int i = 0; i < index; i++) {
              node = node.next;
          }
          return node.task;
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {

            private int index = 0;
            private int previousLoc = -1;
            private Node node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Task next() {
                try {
                    int i = index;
                    Task next = getTask(i);
                    node = node.next;
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
                    LinkedTaskList.this.remove(getTask(previousLoc));
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

        LinkedTaskList list = (LinkedTaskList) o;
        Iterator<Task> itr = this.iterator();
        Iterator<Task> itr2 = list.iterator();
        while (itr.hasNext()) {
            while (itr2.hasNext()) {
                boolean isEqual = itr.next().equals(itr2.next());
                if(!isEqual) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        Iterator<Task> itr = this.iterator();
        int result = 0;
        while (itr.hasNext()) {
            result = 31 * itr.next().hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return "LinkedTaskList{" +
                "head=" + head +
                '}';
    }

    @Override
    public Stream<Task> getStream(){
        Task[] arr = new Task[size()];
        Iterator<Task> itr = this.iterator();

        while (itr.hasNext()) {
            for (int i = 0; i < size(); i++) {
                arr[i] = itr.next();
            }
        }
        return Arrays.stream(arr);
    }

}
