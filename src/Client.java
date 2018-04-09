/**
 * Created by Jvck on 2018/1/8.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import javax.swing.*;

public class Client extends JFrame implements ActionListener {

    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton closeButton;
    private JButton sendButton;

    private Socket mSocket;
    private PrintWriter printWriter = null;
    private BufferedReader bufferedReader = null;


    private Client() {
        super();
        try {
            mSocket = new Socket("127.0.0.1", 2222);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTitle("客户端");
        setBounds(200, 150, 350, 400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(300, 200));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputArea = new JTextArea(6, 25);
        scrollPane.setViewportView(outputArea);

        JPanel mediumJPanel = new JPanel();
        mediumJPanel.setPreferredSize(new Dimension(300, 120));
        inputArea = new JTextArea(4, 27);
        mediumJPanel.add(inputArea);

        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setPreferredSize(new Dimension(300, 60));
        closeButton = new JButton("关闭");
        closeButton.addActionListener(this);
        sendButton = new JButton("发送");
        sendButton.addActionListener(this);
        bottomJPanel.add(closeButton);
        bottomJPanel.add(sendButton);

        getContentPane().add(scrollPane, BorderLayout.NORTH);
        getContentPane().add(mediumJPanel, BorderLayout.CENTER);
        getContentPane().add(bottomJPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    private void run() {

        try {

            InputStream is = mSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            bufferedReader = new BufferedReader(isr);


            String info;
            while ((info = bufferedReader.readLine()) != null) {
                outputArea.append("服务器说：" + info + "\n");
            }

            bufferedReader.close();
            is.close();
            isr.close();

        } catch (Exception ignored) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (closeButton.hasFocus()) {
            try {
                printWriter.close();
                bufferedReader.close();
                mSocket.close();
                System.out.println("客户端已关闭");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (sendButton.hasFocus()) {
            String outMessage = inputArea.getText();
            inputArea.setText(null);
            outputArea.append("客户端说：" + outMessage + "\n");
//            printWriter.println(outMessage);
            try {

                OutputStream os = mSocket.getOutputStream();
                printWriter = new PrintWriter(os);
                printWriter.println(outMessage);
                printWriter.flush();


            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

}
