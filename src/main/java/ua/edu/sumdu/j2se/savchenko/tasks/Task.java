package ua.edu.sumdu.j2se.savchenko.tasks;

import java.time.Duration;
import java.time.LocalDateTime;


public class Task implements Cloneable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;

    public Task(String title, LocalDateTime time) {
        boolean isTimeLarger;

        try {
            isTimeLarger = getSecondsDuration(time) >= 0;
        } catch (Throwable t) {
            throw new IllegalArgumentException("Wrong time");
        }

        if(isTimeLarger){
            this.title = title;
            this.time = time;
        }

    }

    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {
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

    public LocalDateTime getTime() {
        return (isRepeated() ? start : time);
    }

    public void setTime(LocalDateTime time) {
        if(isRepeated()) {
            this.interval = 0;
        }
        this.time = time;
    }

    public LocalDateTime getStartTime() {
        return (isRepeated() ? start : time);
    }

    public LocalDateTime getEndTime() {
        return (isRepeated() ? end : time);
    }

    public int getRepeatInterval() {
        return interval;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
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

    public LocalDateTime nextTimeAfter (LocalDateTime current) {
        if(isActive() && !isRepeated()) {
            return (current.isAfter(this.time)) || current.isEqual(this.time) ? null : this.time;
        }
        if(isActive() & isRepeated()) {
            if(current.isBefore(this.start)) {
                return start;
            }
            else {
                LocalDateTime time = this.start;
                while (current.isAfter(time) || current.isEqual(time)) {
                    time = time.plusSeconds(this.interval);
                }
                if(time.isAfter(this.end)) {
                    return null;
                }
                return time;
            }
        }
        return null;
    }

    private long getSecondsDuration(LocalDateTime time) {
        long hours;
        long minutes;
        long seconds;
        try {
            hours = time.getHour();
            minutes = time.getMinute();
            seconds = time.getSecond();
        } catch (Throwable t) {
            throw t;
        }

        return (hours * 3600) + (minutes * 60) + seconds;
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
        if (time != null) {
            result = 31 * result + time.hashCode();
        }
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
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
