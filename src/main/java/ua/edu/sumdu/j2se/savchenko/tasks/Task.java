package ua.edu.sumdu.j2se.savchenko.tasks;

public class Task implements Cloneable {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;

    public Task(String title, int time) {
        if(time >= 0){
            this.title = title;
            this.time = time;
        } else {
            throw new IllegalArgumentException("Wrong time");
        }

    }

    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return (isRepeated() ? start : time);
    }

    public void setTime(int time) {
        if(isRepeated()) {
            this.interval = 0;
        }
        this.time = time;
    }

    public int getStartTime() {
        return (isRepeated() ? start : time);
    }

    public int getEndTime() {
        return (isRepeated() ? end : time);
    }

    public int getRepeatInterval() {
        return interval;
    }

    public void setTime(int start, int end, int interval) {
        this.start = start;
        this.end = end;
        if (!isRepeated()) {
            this.interval = interval;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRepeated() {
        int interval = getRepeatInterval();
        return interval > 0;
    }

    public int nextTimeAfter (int current) {
        if(isActive() & !isRepeated()) {
            return (current >= this.time) ? -1 : this.time;
        }
        if(isActive() & isRepeated()) {
            if(current < this.start) {
                return start;
            }
            else if((this.end - current) < this.interval) {
                return -1;
            }
            else {
                int time = this.start;
                while (current >= time) {
                    time += this.interval;
                }
                return time;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (time != task.time) return false;
        if (start != task.start) return false;
        if (end != task.end) return false;
        if (interval != task.interval) return false;
        if (active != task.active) return false;
        return title.equals(task.title);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + time;
        result = 31 * result + start;
        result = 31 * result + end;
        result = 31 * result + interval;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        Task cloneTask = (Task) super.clone();
        cloneTask.title = this.title;
        cloneTask.time = this.time;
        cloneTask.start = this.start;
        cloneTask.end = this.end;
        cloneTask.interval = this.interval;
        cloneTask.active = this.active;

        return cloneTask;
    }
}
