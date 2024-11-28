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
    private final TaskManager taskManager;

    public UpdateStatusDialog(TaskManager taskManager) {
        setTitle("Update status");
        this.taskManager = taskManager;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setBounds(screenSize.width / 3, screenSize.width / 4, screenSize.width / 3, screenSize.height / 4);

        buttonOK.addActionListener(e -> updateTaskStatus());

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

    private boolean isDigit(){
        boolean isDigit;
        try {
            Integer.parseInt(textFieldTaskStatus.getText());
            isDigit = true;
            id = Integer.parseInt(textFieldTaskStatus.getText());
        } catch (NumberFormatException e) {
            isDigit = false;
        }
        return isDigit;
    }

    public int updateTaskStatus() {
        if (!textFieldTaskStatus.getText().isEmpty() && isDigit()
                && taskManager.getEpicTask(id) == null) {
            id = Integer.parseInt(textFieldTaskStatus.getText());
            hideAllAndStartTimer("Статус задачи успешно обновлен!");
            return id;
        } else if (!textFieldTaskStatus.getText().isEmpty()
                && isDigit() && taskManager.getEpicTask(id) != null) {
            hideAllAndStartTimer("Нельзя обновлять статус EpicTask!");
            return 0;
        } else {
            hideAllAndStartTimer("Неверный ввод!");
            return 0;
        }
    }

    private void onCancel() {
        dispose();
    }

    private void hideAllAndStartTimer(String setText){
        textFieldTaskStatus.setVisible(false);
        labelTaskStatus.setText(setText);
        buttonOK.setVisible(false);
        getRootPane().setDefaultButton(buttonCancel);
        Timer timer = new Timer(1000, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }

}
