package dialogs;

import model.EpicTask;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddTaskDialog extends JDialog {
    private String taskName;
    private String taskDescription;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldAddTaskName;
    private JLabel labelAddTaskName;
    private JTextField textFieldAddTaskDescription;
    private JLabel labelAddTaskDescription;

    public AddTaskDialog() {
        setTitle("Add task");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setBounds(screenSize.width / 3, screenSize.width / 4, screenSize.width / 4, screenSize.height / 4);

        buttonOK.addActionListener(e -> addTask());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private String deleteSpace(String string) {
        return string.trim();
    }

    public void addTask() {
        String name = deleteSpace(textFieldAddTaskName.getText());
        String description = deleteSpace(textFieldAddTaskDescription.getText());
        if (name.isEmpty()) {
            hideAllAndStartTimer("Введите имя задачи!");
        } else {
            taskName = name;
            taskDescription = description;
            hideAllAndStartTimer("Задача " + taskName + " успешно добавлена!");
        }
    }

    public Task getTask(Boolean isEpic) {
        if (isEpic) {
            Task newTask = new EpicTask(taskName, taskDescription);
            dispose();
            return newTask;
        } else {
            Task newTask = new Task(taskName, taskDescription);
            dispose();
            return newTask;
        }
    }

    private void onCancel() {
        dispose();
    }

    private void hideAllAndStartTimer(String setText) {
        textFieldAddTaskDescription.setVisible(false);
        textFieldAddTaskName.setVisible(false);
        labelAddTaskDescription.setText(setText);
        labelAddTaskName.setVisible(false);
        buttonOK.setVisible(false);
        getRootPane().setDefaultButton(buttonCancel);
        Timer timer = new Timer(1000, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }

}
