package dialogs;

import controllers.InMemoryTaskManager;
import controllers.TaskManager;
import model.EpicTask;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainDialog extends JDialog {
    private TaskManager taskManager = new InMemoryTaskManager();
    private int tasksNum = 0;
    private JPanel contentPane;
    private JButton exitButton;
    private JButton updateTaskStatusButton;
    private JButton deleteTaskButton;
    private JButton addNewTaskButton;
    private JButton addNewSubTaskButton;
    private DefaultListModel<Task> listModelTask = new DefaultListModel<>();
    private JList<Task> listTask;
    private JButton printTasksByStatusButton;
    private JButton addNewEpicTaskButton;
    private JButton deleteAllTasksButton;
    private JButton printAllTasksButton;


    public MainDialog() {
        setTitle("TaskManager");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(addNewTaskButton);
        setBounds(screenSize.width / 4, screenSize.height / 4, screenSize.width / 2 + 100, screenSize.height / 2 + 100);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        });

        addNewTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddTask();
            }
        });

        addNewEpicTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddEpicTask();
            }
        });

        printTasksByStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printTasksByStatus();
            }
        });

        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeleteTask();
            }
        });

        deleteAllTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeleteAllTasks();
            }
        });

        updateTaskStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUpdateTaskStatus();
            }
        });

        printAllTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printAllTasks();
            }
        });

        addNewSubTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewSubTask();
            }
        });


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onExit() {
        dispose();
    }

    public void onUpdateTaskStatus() {
        UpdateStatusDialog dialog = new UpdateStatusDialog(taskManager);
        dialog.setVisible(true);
        if (dialog.updateTaskStatus() != 0) {
            taskManager.updateTaskStatus(dialog.updateTaskStatus());
            listModelTask.removeAllElements();
            listModelTask.addAll(taskManager.printAllTasks());
            listTask.setModel(listModelTask);
        }
    }

    private void onAddEpicTask() {
        AddTaskDialog addTaskDialog = new AddTaskDialog();
        addTaskDialog.setVisible(true);
        if (addTaskDialog.getTask(true).getTaskName().equals("")) {
            listModelTask.removeAllElements();
            listModelTask.addAll(taskManager.printAllTasks());
            listTask.setModel(listModelTask);
        } else {
            tasksNum++;
            taskManager.addNewEpicTask((EpicTask)addTaskDialog.getTask(true));
            listModelTask.removeAllElements();
            listModelTask.addAll(taskManager.printAllTasks());
            listTask.setModel(listModelTask);
        }
    }

    private void onAddTask() {
        AddTaskDialog addTaskDialog = new AddTaskDialog();
        addTaskDialog.setVisible(true);
        if (addTaskDialog.getTask(false).getTaskName().equals("")) {
            listModelTask.removeAllElements();
            listModelTask.addAll(taskManager.printAllTasks());
            listTask.setModel(listModelTask);
        } else {
            tasksNum++;
            taskManager.addNewTask(addTaskDialog.getTask(false));
            listModelTask.removeAllElements();
            listModelTask.addAll(taskManager.printAllTasks());
            listTask.setModel(listModelTask);
        }
    }

    private void onDeleteTask() {
        DeleteTaskDialog dialog = new DeleteTaskDialog(taskManager);
        dialog.setVisible(true);
        listModelTask.removeElement(taskManager.getTask(dialog.deleteTask()));
        listModelTask.removeElement(taskManager.getEpicTask(dialog.deleteTask()));
        listModelTask.removeElement(taskManager.getSubTask(dialog.deleteTask()));
        taskManager.deleteById(dialog.deleteTask());
        listTask.setModel(listModelTask);
    }

    private void onDeleteAllTasks() {
        listModelTask.removeAllElements();
        taskManager.deleteTasks();
        listTask.setModel(listModelTask);
    }


    private void printTasksByStatus() {
        PrintByStatusDialog dialog = new PrintByStatusDialog();
        dialog.setVisible(true);
        DefaultListModel<Task> listModelByStatus = new DefaultListModel<>();
        listModelByStatus.addAll(taskManager.printTasksByStatus(dialog.getStatus()));
        listTask.setModel(listModelByStatus);
    }

    private void printAllTasks() {
        listModelTask.removeAllElements();
        listModelTask.addAll(taskManager.printAllTasks());
        listTask.setModel(listModelTask);
    }

    private void addNewSubTask() {
        AddSubTaskDialog dialog = new AddSubTaskDialog(taskManager);
        dialog.setVisible(true);

        if (dialog.addSubTask().getTaskName().equals("")) {
            listModelTask.removeAllElements();
            listModelTask.addAll(taskManager.printAllTasks());
            listTask.setModel(listModelTask);
        } else {
            tasksNum++;
            taskManager.addNewSubTask(dialog.addSubTask(), dialog.getTaskId());
            listModelTask.removeAllElements();
            listModelTask.addAll(taskManager.printAllTasks());
            listTask.setModel(listModelTask);
        }
    }
}
