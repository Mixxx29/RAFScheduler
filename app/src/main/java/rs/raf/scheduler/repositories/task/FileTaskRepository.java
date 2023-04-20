package rs.raf.scheduler.repositories.task;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rs.raf.scheduler.models.Model;
import rs.raf.scheduler.models.Task;
import rs.raf.scheduler.repositories.IModelRepository;

public class FileTaskRepository implements IModelRepository {

    private Context context;

    private List<Task> tasks;

    public FileTaskRepository(Context context) throws FileNotFoundException {
        this.context = context;

        // Initialize users list
        tasks = new ArrayList<>();

        // Load users
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.openFileInput("tasks.txt")));

        String message;
        try {
            while ((message = reader.readLine()) != null) {
                String[] task = message.split("\\|");
                tasks.add(new Task(task[0], task[1], task[2], task[3], Task.Priority.valueOf(task[4]), task[5]));
                System.out.println(Arrays.toString(task));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Model> getAll() {
        return null;
    }

    @Override
    public List<Model> getAll(String s) {
        List<Model> filtered = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDate().equals(s)) filtered.add(task);
        }
        return filtered;
    }

    @Override
    public Model get(String s) {
        for (Task task : tasks) {
            if (task.getTitle().equals(s)) return task;
        }
        return null;
    }

    @Override
    public Model create(Model model) {
        if (!(model instanceof Task)) return null;
        tasks.add((Task) model);
        return model;
    }

    @Override
    public boolean delete(Model model) {
        if (!(model instanceof Task)) return false;
        tasks.remove((Task) model);
        return true;
    }

    @Override
    public boolean save(Model model) {
        // Get file stream
        try {
            PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(
                            context.openFileOutput("tasks.txt", Context.MODE_PRIVATE)), true);

            // Write all users
            for (Task task : tasks) {
                writer.println(task.getDate() + "|" + task.getTitle() + "|" + task.getStartTime() +
                        "|" + task.getEndTime() + "|" + task.getPriority() + "|" +
                        task.getDescription());
            }
            writer.close();
            return true;
        } catch (FileNotFoundException e) {

        }
        return false;
    }
}

