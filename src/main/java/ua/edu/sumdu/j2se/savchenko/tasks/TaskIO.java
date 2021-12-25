package ua.edu.sumdu.j2se.savchenko.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.Objects;

public class TaskIO {

    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (DataOutputStream outStream = new DataOutputStream(out)) {
            outStream.writeInt(tasks.size());
            for (Task task : tasks) {
                if (task != null) {
                    outStream.writeInt(task.getTitle().length());
                    outStream.writeUTF(task.getTitle());
                    outStream.writeBoolean(task.isActive());
                    outStream.writeInt(task.getRepeatInterval());
                    if (task.isRepeated()) {
                        outStream.writeLong(task.getStartTime().atZone(ZoneId.systemDefault()).toEpochSecond());
                        outStream.writeLong(task.getStartTime().atZone(ZoneId.systemDefault()).toEpochSecond());
                    } else {
                        outStream.writeLong(task.getStartTime().atZone(ZoneId.systemDefault()).toEpochSecond());
                    }
                }
            }
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (DataInputStream inStream = new DataInputStream(in)) {
            ArrayTaskList list = new ArrayTaskList();
            int size = inStream.readInt();
            while (inStream.available() > 0) {
                Task task = new Task();
                int length = inStream.readInt();
                task.setTitle(inStream.readUTF());
                task.setActive(inStream.readBoolean());
                task.setRepeatInterval(inStream.readInt());
                if(task.getRepeatInterval() > 0) {
                    task.setStartTime(LocalDateTime.ofEpochSecond(inStream.readLong(), 0, ZoneOffset.UTC));
                    task.setEndTime(LocalDateTime.ofEpochSecond(inStream.readLong(), 0, ZoneOffset.UTC));
                } else {
                    task.setTime(LocalDateTime.ofEpochSecond(inStream.readLong(), 0, ZoneOffset.UTC));
                }
                list.add(task);
            }
            for (Task task : list) {
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {

        try (FileOutputStream writer = new FileOutputStream(file)) {
            write(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readBinary(AbstractTaskList tasks, File file) {

        try (FileInputStream reader = new FileInputStream(file)) {
            read(tasks, reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void write(AbstractTaskList tasks, Writer out) {

        ArrayTaskList list = new ArrayTaskList();
        tasks.getStream().filter(Objects::nonNull).forEach(list::add);
        Gson jsonBuilder = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .setPrettyPrinting().create();
        try (BufferedWriter bufferedWriter = new BufferedWriter(out)) {
            jsonBuilder.toJson(list, bufferedWriter);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void read(AbstractTaskList tasks, Reader in) {

        Gson jsonBuilder = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
        AbstractTaskList list = new LinkedTaskList();
        try (BufferedReader bufferedReader = new BufferedReader(in)) {
            list = jsonBuilder.fromJson(bufferedReader, ArrayTaskList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.getStream().forEach(tasks::add);
    }

    public static void writeText(AbstractTaskList tasks, File file) {

        try (FileWriter writer = new FileWriter(file)) {
            write(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readText(AbstractTaskList tasks, File file) {

        try (FileReader reader = new FileReader(file)) {
            read(tasks, reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
