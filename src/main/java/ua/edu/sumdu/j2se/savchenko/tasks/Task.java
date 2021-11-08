package ua.edu.sumdu.j2se.savchenko.tasks;

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;

    public Task(String title, int time) {
        this.title = title;
        this.time = time;
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
}
