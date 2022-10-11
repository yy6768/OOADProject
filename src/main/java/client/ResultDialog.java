package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 用于显示结果
 */
public class ResultDialog extends JDialog {
    private final PlayerClient client;
    private final String content;

    public ResultDialog(PlayerClient client, String text) {
        super();
        setTitle("游戏结束");
        this.client = client;
        this.content = text;
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                client.backToIndex();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        initLabel();
        initButtons();
    }

    private void initLabel() {
        JLabel label = new JLabel(content);
        int labelX = getWidth() / 5;
        int labelY =  getHeight() / 3;
        int labelWidth = getWidth() * 4 / 5;
        int labelHeight = getHeight() / 10;
        int fontSize = getHeight() / 10;
        label.setFont(new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, fontSize));
        label.setBounds(labelX, labelY, labelWidth, labelHeight);
        add(label);
    }

    private void initButtons() {
        int buttonX = getWidth() / 6;
        int buttonY = getHeight() / 2;
        int buttonWidth = getWidth() / 4;
        int buttonHeight = getHeight() / 10;
        int fontSize = getHeight() / 15;
        JButton goOnButton = new JButton("继续");
        JButton cancelButton = new JButton("结束");
        Font buttonFont = new Font(Font.DIALOG, Font.ITALIC, fontSize);

        goOnButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        goOnButton.setFont(buttonFont);
        goOnButton.addActionListener(e -> {
            client.startGame();
            this.dispose();
        });

        cancelButton.setBounds(buttonX + getWidth() / 3, buttonY, buttonWidth, buttonHeight);
        cancelButton.setFont(buttonFont);
        cancelButton.addActionListener(e -> {
            client.backToIndex();
            this.dispose();
        });

        add(goOnButton);
        add(cancelButton);
    }
}
