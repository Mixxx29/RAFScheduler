package rs.raf.scheduler.repositories.user;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import rs.raf.scheduler.models.Model;
import rs.raf.scheduler.models.User;
import rs.raf.scheduler.repositories.IModelRepository;

public class FileUserRepository implements IModelRepository {

    private List<User> users;

    private Context context;

    public FileUserRepository(Context context) throws FileNotFoundException {
        this.context = context;

        // Initialize users list
        users = new ArrayList<>();

        // Load users
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.openFileInput("users.txt")));

        String message;
        try {
            while ((message = reader.readLine()) != null) {
                String[] user = message.split("\\|");
                users.add(new User(user[0], user[1], user[2]));
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
        return new ArrayList<>();
    }

    @Override
    public Model get(String s) {
        for (User user : users) {
            if (user.getUsername().equals(s)) return user;
        }
        return null;
    }

    @Override
    public Model create(Model model) {
        return null;
    }

    @Override
    public boolean delete(Model model) {
        return false;
    }

    @Override
    public boolean save(Model model) {
        // Get file stream
        try {
            PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(
                            context.openFileOutput("users.txt", Context.MODE_PRIVATE)), true);

            // Write all users
            for (User user : users) {
                writer.println(user.getEmail() + "|" + user.getUsername() + "|" + user.getPassword());
            }
            writer.close();
            return true;
        } catch (FileNotFoundException e) {

        }
        return false;
    }
}
