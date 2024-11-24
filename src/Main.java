import controllers.InMemoryTaskManager;
import controllers.TaskManager;
import dialogs.MainDialog;
import utilis.TaskSerializer;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = TaskSerializer.loadTaskManager();
        MainDialog mainDialog = new MainDialog(taskManager);
        mainDialog.setVisible(true);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            TaskSerializer.saveTaskManager(taskManager);
            System.out.println("TaskManager saved before shutdown.");
        }));
    }
}