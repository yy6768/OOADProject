package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 结果面板
 * 包含结果信息和两个跳转界面按钮
 */
public class ResultDialog extends JDialog {
    //当前界面客户端
    private final PlayerClient client;
    //当前界面结果信息
    private final String content;

    /**
     * 构造函数
     * @param client 客户端
     * @param text 输出文本
     */
    public ResultDialog(PlayerClient client, String text) {
        super();
        setTitle("游戏结束");
        this.client = client;
        this.content = text;
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            /**
             * 当关闭窗口时跳转到首页
             * @param e
             */
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

    /**
     * 初始化文字信息
     */
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

    /**
     * 初始化按钮
     * goOnButton用于新启动一局游戏
     * cancelButton 用于返回到起始界面
     */
    private void initButtons() {
        int buttonX = getWidth() / 3;
        int buttonY = getHeight() / 2;
        int buttonWidth = getWidth() / 4;
        int buttonHeight = getHeight() / 10;
        int fontSize = getHeight() / 15;
        JButton cancelButton = new JButton("结束");
        Font buttonFont = new Font(Font.DIALOG, Font.ITALIC, fontSize);

        cancelButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        cancelButton.setFont(buttonFont);
        cancelButton.addActionListener(e -> {
            client.backToIndex();
            this.dispose();
        });

        add(cancelButton);
    }
}
