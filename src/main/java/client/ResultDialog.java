package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * ������
 * ���������Ϣ��������ת���水ť
 */
public class ResultDialog extends JDialog {
    //��ǰ����ͻ���
    private final PlayerClient client;
    //��ǰ��������Ϣ
    private final String content;

    /**
     * ���캯��
     * @param client �ͻ���
     * @param text ����ı�
     */
    public ResultDialog(PlayerClient client, String text) {
        super();
        setTitle("��Ϸ����");
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
             * ���رմ���ʱ��ת����ҳ
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
     * ��ʼ��������Ϣ
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
     * ��ʼ����ť
     * goOnButton����������һ����Ϸ
     * cancelButton ���ڷ��ص���ʼ����
     */
    private void initButtons() {
        int buttonX = getWidth() / 3;
        int buttonY = getHeight() / 2;
        int buttonWidth = getWidth() / 4;
        int buttonHeight = getHeight() / 10;
        int fontSize = getHeight() / 15;
        JButton cancelButton = new JButton("����");
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
