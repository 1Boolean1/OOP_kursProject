package dialogs;

import controllers.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteTaskDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel label;
    private final TaskManager taskManager;

    public DeleteTaskDialog(TaskManager taskManager) {
        setTitle("Delete task");
        this.taskManager = taskManager;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 3, screenSize.height / 3, screenSize.width / 3, screenSize.height / 3);

        buttonOK.addActionListener(e -> deleteTask());

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
            Integer.parseInt(textField1.getText());
            isDigit = true;
        } catch (NumberFormatException e) {
            isDigit = false;
        }
        return isDigit;
    }

    private boolean checkForNull(){
        boolean isNull = true;
        int id = Integer.parseInt(textField1.getText());
        if (taskManager.getTask(id) != null) {
            isNull = false;
        } else if (taskManager.getEpicTask(id) != null) {
            isNull = false;
        } else if (taskManager.getSubTask(id) != null) {
            isNull = false;
        }
        return isNull;
    }

    public int deleteTask() {
        if (!textField1.getText().isEmpty() && isDigit()) {
            int id = Integer.parseInt(textField1.getText());
            if (!checkForNull()) {
                hideAllAndStartTimer("Задача успешно удалена");
                return id;
            } else {
                hideAllAndStartTimer("Задачи с таким id нет!");
                return 0;
            }
        } else {
            hideAllAndStartTimer("Неверный ввод!");
            return 0;
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void hideAllAndStartTimer(String setText){
        textField1.setVisible(false);
        label.setText(setText);
        buttonOK.setVisible(false);
        getRootPane().setDefaultButton(buttonCancel);
        Timer timer = new Timer(1000, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }
}
