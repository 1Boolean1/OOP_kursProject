package dialogs;

import controllers.TaskManager;
import model.EpicTask;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainDialog extends JDialog {
    TaskManager taskManager;
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
    private JButton updateTaskButton;


    public MainDialog(TaskManager taskManager) {
        this.taskManager = taskManager;
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

        updateTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUpdateTask();
            }
        });


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        printList();
    }

    private void onExit() {
        dispose();
    }

    public void onUpdateTaskStatus() {
        UpdateStatusDialog dialog = new UpdateStatusDialog(taskManager);
        dialog.setVisible(true);
        if (dialog.updateTaskStatus() != 0) {
            taskManager.updateTaskStatus(dialog.updateTaskStatus());
            printList();
        }
    }

    private void onAddEpicTask() {
        AddTaskDialog addTaskDialog = new AddTaskDialog();
        addTaskDialog.setVisible(true);
        if (addTaskDialog.getTask(true).getTaskName().equals("")) {
            printList();
        } else {
            tasksNum++;
            taskManager.addNewEpicTask((EpicTask)addTaskDialog.getTask(true));
            printList();
        }
    }

    private void onAddTask() {
        AddTaskDialog addTaskDialog = new AddTaskDialog();
        addTaskDialog.setVisible(true);
        if (addTaskDialog.getTask(false).getTaskName().equals("")) {
            printList();
        } else {
            tasksNum++;
            taskManager.addNewTask(addTaskDialog.getTask(false));
            printList();
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
        printList();
    }


    private void printTasksByStatus() {
        PrintByStatusDialog dialog = new PrintByStatusDialog();
        dialog.setVisible(true);
        DefaultListModel<Task> listModelByStatus = new DefaultListModel<>();
        listModelByStatus.addAll(taskManager.printTasksByStatus(dialog.getStatus()));
        listTask.setModel(listModelByStatus);
    }

    private void printAllTasks() {
        printList();
    }

    private void addNewSubTask() {
        AddSubTaskDialog dialog = new AddSubTaskDialog(taskManager);
        dialog.setVisible(true);

        if (dialog.addSubTask().getTaskName().equals("")) {
            printList();
        } else {
            tasksNum++;
            taskManager.addNewSubTask(dialog.addSubTask(), dialog.getTaskId());
            printList();
        }
    }

    private void onUpdateTask(){
        UpdateTaskDialog dialog = new UpdateTaskDialog(taskManager);
        dialog.setVisible(true);
        printList();
    }

    private void printList(){
        listModelTask.removeAllElements();
        listModelTask.addAll(taskManager.printAllTasks());
        listTask.setModel(listModelTask);
    }
}
