import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.net.*;

public class SADChat {

    static InetAddress chatURL;

    String      appName     = "SAD Chat";
    SADChat     chat;
    JFrame      newFrame    = new JFrame(appName);
    
    JButton     sendMessage;
    JTextField  messageBox;
    JTextArea   chatList;
	JTextArea	usersList;

    JTextField  usernameChooser;
    JFrame      preFrame;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SADChat chat = new SADChat();
                
                try{
                    chatURL     = InetAddress.getByName("127.0.0.1");
                }catch(Exception e){}

                chat.preDisplay();
            }
        });
    }

    public void preDisplay() {
        newFrame.setVisible(false);
        preFrame = new JFrame(appName);
        
        usernameChooser = new JTextField(15);
        usernameChooser.addActionListener( new ActionListener(){

                public void actionPerformed(ActionEvent e){

                        new enterServerButtonListener();

                }} );
        JLabel chooseUsernameLabel = new JLabel("Tria el teu nick:");
        JButton enterServer = new JButton("Entrar al chat");
        enterServer.addActionListener(new enterServerButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        preRight.weightx = 2.0;
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        prePanel.add(chooseUsernameLabel, preLeft);
        prePanel.add(usernameChooser, preRight);
        preFrame.add(BorderLayout.CENTER, prePanel);
        preFrame.add(BorderLayout.SOUTH, enterServer);
        preFrame.setSize(300, 300);
        preFrame.setVisible(true);

    }

    public void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.GRAY);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();
        messageBox.addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e){

                        new sendMessageButtonListener();

                }});

        sendMessage = new JButton("Envia Missatge");
        sendMessage.addActionListener(new sendMessageButtonListener());

        chatList = new JTextArea();
        chatList.setEditable(false);
        chatList.setFont(new Font("Serif", Font.PLAIN, 15));
        chatList.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatList), BorderLayout.CENTER);

        usersList = new JTextArea();
        usersList.setEditable(false);
        usersList.setFont(new Font("Serif", Font.PLAIN, 15));
        usersList.setLineWrap(true);
        usersList.append("<" + username + "> (Me)");

        mainPanel.add(new JScrollPane(usersList), BorderLayout.EAST);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(470, 300);
        newFrame.setVisible(true);
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                
            } else if (messageBox.getText().equals("/clear")) {
                chatList.setText("Cleared all messages\n");
                messageBox.setText("");
            } else if (messageBox.getText().equals("/exit")) {
                System.exit(0);
            } else {
                chatList.append("<" + username + ">:  " + messageBox.getText()
                        + "\n");
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }

    String  username;

    class enterServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            username = usernameChooser.getText();
            if (username.length() < 1) {
                System.out.println("No!");
            } else {
                preFrame.setVisible(false);
                display();
            }
        }

    }
}