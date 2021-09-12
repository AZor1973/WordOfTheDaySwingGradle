package ru.azor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class WordFrame extends JFrame {
    private int startX;
    private int startY;
    private boolean isClicked;

    private WordFrame() {
        setBounds(400, 100, 600, 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setOpacity(0.5f);
        setAlwaysOnTop(true);
        Font font = new Font("Times New Roman", Font.BOLD, 36);
        JLabel jLabel = new JLabel("Word of the Day", JLabel.CENTER);
        jLabel.setFont(font);
        add(jLabel);
        DataBaseService.createConnection();
        DataBaseService.setPrepareStatements();
        jLabel.setText(DataBaseService.getWord());
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                jLabel.setText(DataBaseService.getWord());
            }
        }, 0, TimeUnit.MINUTES.toMillis(5));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    isClicked = false;
                    jLabel.setText(DataBaseService.getWord());
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) {
                    isClicked = true;
                    startX = e.getX();
                    startY = e.getY();
                } else {
                    addFrame();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent de) {
                if (isClicked) {
                    int dX = de.getX() - startX;
                    int dY = de.getY() - startY;
                    setLocation(getX() + dX, getY() + dY);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataBaseService.closeConnection();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(WordFrame::new);
    }

    private static void addFrame() {
        EventQueue.invokeLater(AddFrame::new);
    }
}
