package dialogs;

import controllers.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteTaskDialog extends JDialog {
    private int id;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel label;
    private boolean isNull = true;
    private TaskManager taskManager;

    public DeleteTaskDialog(TaskManager taskManager) {
        this.taskManager = taskManager;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 3, screenSize.height / 3, screenSize.width / 3, screenSize.height / 3);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTask();
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

    public int deleteTask() {
        boolean isDigit = false;
        try {
            Integer.parseInt(textField1.getText());
            isDigit = true;
        } catch (NumberFormatException e) {
            isDigit = false;
        }
        if (!textField1.getText().isEmpty() && isDigit) {
            id = Integer.parseInt(textField1.getText());
            if (taskManager.getTask(id) != null) {
                isNull = false;
            } else if (taskManager.getEpicTask(id) != null) {
                isNull = false;
            } else if (taskManager.getSubTask(id) != null) {
                isNull = false;
            }
            if (!isNull) {
                label.setText("Задача успешно удалена");
                textField1.setVisible(false);
                buttonOK.setVisible(false);
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                timer.setRepeats(false);
                timer.start();
                return id;
            } else {
                label.setText("Задачи с таким id нет!");
                textField1.setVisible(false);
                buttonOK.setVisible(false);
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                timer.setRepeats(false);
                timer.start();
                return 0;
            }
        } else {
            textField1.setVisible(false);
            label.setText("Неверный ввод!");
            buttonOK.setVisible(false);
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            timer.setRepeats(false);
            timer.start();
            return 0;
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
