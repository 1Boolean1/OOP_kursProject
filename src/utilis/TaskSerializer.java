package utilis;

import controllers.InMemoryTaskManager;
import controllers.TaskManager;

import java.io.*;

public class TaskSerializer {
    private static final String FILE_NAME = "task_manager.ser";

    public static void saveTaskManager(TaskManager taskManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(taskManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TaskManager loadTaskManager() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            TaskManager taskManager = (TaskManager) ois.readObject();

            return taskManager;
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new InMemoryTaskManager();
    }
}
