package ua.edu.sumdu.j2se.savchenko.tasks;


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
            throw new NullPointerException("Task must not be null");
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
            throw new IndexOutOfBoundsException("Index must be less or equal to elements amount");
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
    public LinkedTaskList incoming(int from, int to) {
        LinkedTaskList incoming = new LinkedTaskList();
        Node node = head;
        while (node.next != null){
            if(node.task.nextTimeAfter(from) != -1) {
                if (node.task.getStartTime() >= from && node.task.getEndTime() <= to) {
                        incoming.add(node.task);
                }
            }
            node = node.next;
        }
        return incoming;
    }
}
