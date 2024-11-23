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

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void addTask() {
        if(textFieldAddTaskName.getText().isEmpty()){
            hideAllAndStartTimer("Введите имя задачи!");
        } else {
            taskName = textFieldAddTaskName.getText();
            taskDescription = textFieldAddTaskDescription.getText();
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
        // add your code here if necessary
        dispose();
    }

    private void hideAllAndStartTimer(String setText){
        textFieldAddTaskDescription.setVisible(false);
        textFieldAddTaskName.setVisible(false);
        labelAddTaskDescription.setText(setText);
        labelAddTaskName.setVisible(false);
        buttonOK.setVisible(false);
        getRootPane().setDefaultButton(buttonCancel);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

}
