package dialogs;

import controllers.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateStatusDialog extends JDialog {
    private int id;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldTaskStatus;
    private JLabel labelTaskStatus;
    private TaskManager taskManager;

    public UpdateStatusDialog(TaskManager taskManager) {
        this.taskManager = taskManager;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setBounds(screenSize.width / 3, screenSize.width / 4, screenSize.width / 4, screenSize.height / 4);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTaskStatus();
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

    public int updateTaskStatus() {
        boolean isDigit = false;
        try {
            Integer.parseInt(textFieldTaskStatus.getText());
            isDigit = true;
            id = Integer.parseInt(textFieldTaskStatus.getText());
        } catch (NumberFormatException e) {
            isDigit = false;
        }
        if (!textFieldTaskStatus.getText().isEmpty() && isDigit && taskManager.getEpicTask(id) == null) {
            id = Integer.parseInt(textFieldTaskStatus.getText());
            textFieldTaskStatus.setVisible(false);
            labelTaskStatus.setText("Статус задачи успешно обновлен!");
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
        } else if (!textFieldTaskStatus.getText().isEmpty() && isDigit && taskManager.getEpicTask(id) != null) {
            textFieldTaskStatus.setVisible(false);
            labelTaskStatus.setText("Нельзя обновлять статус EpicTask!");
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
        } else {
            textFieldTaskStatus.setVisible(false);
            labelTaskStatus.setText("Неверный ввод!");
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
        dispose();
    }

}
