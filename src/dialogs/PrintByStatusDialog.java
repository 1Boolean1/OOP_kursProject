package dialogs;

import enums.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrintByStatusDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel label;

    public PrintByStatusDialog() {
        setTitle("Print by status");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setBounds(screenSize.width / 3, screenSize.width / 4, screenSize.width / 3, screenSize.height / 4);

        buttonOK.addActionListener(e -> getStatus());

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

    public Status getStatus() {
        if(textField1.getText().equals("NEW") || textField1.getText().equals("IN_PROGRESS") || textField1.getText().equals("DONE")){
            Status status = Status.valueOf(textField1.getText());
            dispose();
            return status;
        }
        else {
            hideAllAndStartTimer("Неверный ввод");
            return null;
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void hideAllAndStartTimer(String setText){
        label.setText(setText);
        textField1.setVisible(false);
        buttonOK.setVisible(false);
        getRootPane().setDefaultButton(buttonCancel);
        Timer timer = new Timer(1000, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }
}
