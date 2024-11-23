package dialogs;

import controllers.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateTaskDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel updateName;
    private JLabel idTask;
    private JLabel updateDescription;
    private JTextField textFieldId;
    private JTextField textFieldName;
    private JTextField textFieldDescription;
    private TaskManager taskManager;

    public UpdateTaskDialog(TaskManager taskManager) {
        setTitle("Update task");
        this.taskManager = taskManager;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 3, screenSize.width / 4, screenSize.width / 4, screenSize.height / 3);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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

    private void onOK() {
        boolean isDigit = false;
        try {
            Integer.parseInt(textFieldId.getText());
            isDigit = true;
        } catch (NumberFormatException e) {
            isDigit = false;
        }
        if (isDigit) {
            if (taskManager.getTask(Integer.parseInt(textFieldId.getText())) != null) {
                if (textFieldDescription.getText().equals("")) {
                    taskManager.getTask(Integer.parseInt(textFieldId.getText())).
                            setTaskName(textFieldName.getText());
                } else if (textFieldName.getText().equals("")) {
                    taskManager.getTask(Integer.parseInt(textFieldId.getText())).
                            setTaskDescription(textFieldDescription.getText());
                } else {
                    taskManager.getTask(Integer.parseInt(textFieldId.getText())).
                            setTaskDescription(textFieldDescription.getText());
                    taskManager.getTask(Integer.parseInt(textFieldId.getText())).
                            setTaskName(textFieldName.getText());
                }
                hideAndStartTimer("Задача обновлена");
            } else if (taskManager.getEpicTask(Integer.parseInt(textFieldId.getText())) != null) {
                if (textFieldDescription.getText().equals("")) {
                    taskManager.getEpicTask(Integer.parseInt(textFieldId.getText())).
                            setTaskName(textFieldName.getText());
                } else if (textFieldName.getText().equals("")) {
                    taskManager.getEpicTask(Integer.parseInt(textFieldId.getText())).
                            setTaskDescription(textFieldDescription.getText());
                } else {
                    taskManager.getEpicTask(Integer.parseInt(textFieldId.getText())).
                            setTaskDescription(textFieldDescription.getText());
                    taskManager.getEpicTask(Integer.parseInt(textFieldId.getText())).
                            setTaskName(textFieldName.getText());
                }
                hideAndStartTimer("Задача обновлена");
            } else if (taskManager.getSubTask(Integer.parseInt(textFieldId.getText())) != null) {
                if (textFieldDescription.getText().equals("")) {
                    taskManager.getSubTask(Integer.parseInt(textFieldId.getText())).
                            setTaskName(textFieldName.getText());
                } else if (textFieldName.getText().equals("")) {
                    taskManager.getSubTask(Integer.parseInt(textFieldId.getText())).
                            setTaskDescription(textFieldDescription.getText());
                } else {
                    taskManager.getSubTask(Integer.parseInt(textFieldId.getText())).
                            setTaskDescription(textFieldDescription.getText());
                    taskManager.getSubTask(Integer.parseInt(textFieldId.getText())).
                            setTaskName(textFieldName.getText());
                }
                hideAndStartTimer("Задача обновлена");
            } else {
                hideAndStartTimer("Задачи с таким id нет!");
            }
        } else {
            hideAndStartTimer("Неверный ввод!");
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void hideAndStartTimer(String setText){
        textFieldId.setVisible(false);
        textFieldName.setVisible(false);
        textFieldDescription.setVisible(false);
        updateName.setVisible(false);
        updateDescription.setVisible(false);
        buttonOK.setVisible(false);
        idTask.setText(setText);
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
