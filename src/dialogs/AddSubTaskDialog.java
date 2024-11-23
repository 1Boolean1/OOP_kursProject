package dialogs;

import controllers.TaskManager;
import model.SubTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddSubTaskDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelId;
    private JLabel labelName;
    private JLabel labelDescription;
    private JTextField textFieldName;
    private JTextField textFieldDescription;
    private JTextField textFieldId;
    private String taskName;
    private String taskDescription;
    private TaskManager taskManager;

    public int getTaskId() {
        return taskId;
    }

    private int taskId;

    public AddSubTaskDialog(TaskManager taskManager) {
        setTitle("Add sub task");
        this.taskManager = taskManager;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setBounds(screenSize.width / 3, screenSize.height / 3, screenSize.width / 3, screenSize.height / 3);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSubTask();
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

    public SubTask addSubTask() {
        boolean isDigit;
        try {
            Integer.parseInt(textFieldId.getText());
            isDigit = true;
        } catch (NumberFormatException e) {
            isDigit = false;
        }
        if (isDigit) {
            taskId = Integer.parseInt(textFieldId.getText());
            if (taskManager.getEpicTask(taskId) == null) {
                hideAllAndStartTimer("EpicTask с таким id не существует!");
                return null;
            } else {
                if (textFieldName.getText().isEmpty()) {
                    hideAllAndStartTimer("Введите имя задачи!");
                    return null;
                } else {
                    taskName = textFieldName.getText();
                    taskDescription = textFieldDescription.getText();
                    hideAllAndStartTimer("Подзадача " + taskName + " успешно добавлена");
                    SubTask subTask = new SubTask(taskName, taskDescription);
                    return subTask;
                }
            }
        } else {
            hideAllAndStartTimer("Неверный ввод");
            return null;
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void hideAllAndStartTimer(String setText){
        textFieldDescription.setVisible(false);
        textFieldName.setVisible(false);
        textFieldId.setVisible(false);
        labelId.setVisible(false);
        labelDescription.setVisible(false);
        buttonOK.setVisible(false);
        labelName.setText(setText);
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
