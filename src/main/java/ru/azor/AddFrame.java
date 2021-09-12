package ru.azor;

import javax.swing.*;
import java.awt.*;

public class AddFrame extends JFrame {
    public AddFrame() {
        setBounds(400, 150, 500, 80);
        setTitle("Enter a new word with translation");
        setResizable(false);
        JTextField jTextField = new JTextField("", JTextField.CENTER);
        Font font = new Font("Times New Roman", Font.BOLD, 24);
        jTextField.setFont(font);
        jTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5, true));
        add(jTextField);
        jTextField.addActionListener(e -> {
            DataBaseService.setWord(jTextField.getText());
            setVisible(false);
        });

        setVisible(true);
    }
}
