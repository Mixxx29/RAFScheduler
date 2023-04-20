package rs.raf.scheduler.applications;

import android.app.Application;

import java.io.FileNotFoundException;

import rs.raf.scheduler.models.Task;
import rs.raf.scheduler.models.User;
import rs.raf.scheduler.repositories.IModelRepository;
import rs.raf.scheduler.repositories.task.FileTaskRepository;
import rs.raf.scheduler.repositories.user.FileUserRepository;
import timber.log.Timber;

public class App extends Application {

    private static IModelRepository userRepository;
    private static IModelRepository taskRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        inject();
    }

    private void inject() {
        try {
            // Inject users repository
            userRepository = new FileUserRepository(getApplicationContext());

            // Inject tasks repository
            taskRepository = new FileTaskRepository(getApplicationContext());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static IModelRepository getUserRepository() {
        return userRepository;
    }

    public static IModelRepository getTaskRepository() {
        return taskRepository;
    }
}
