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

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;

class Gui implements Observer{

	JButton     sendMessage;
    JTextField  messageBox;
    JTextArea   chatList;
	JTextArea	usersList;
    JFrame      newFrame;

    JLabel      chooseUsernameLabel;
    JLabel      errorLabel;
    JTextField  usernameChooser;
    JButton     enterServerButton;
    JFrame      preFrame;

    String appName;
    Client client;
    ArrayList<String> connectedUsers;

    public Gui(String appName, InetAddress chatURL, int chatPort){
        newFrame    = new JFrame(appName);
        this.appName = appName;
        this.connectedUsers = new ArrayList<String>();
        this.preDisplay(chatURL, chatPort);
        
    }

    public void update(Observable c, Object linia){
        this.client = (Client) c;
        String l = (String) linia;

        String[] seq = l.split(" ");
        String command = seq[0];
        if(command.equals("USR:")){        
            updateUsersList(seq[1], seq[2]);
        }

        else if(command.equals("MSG:")){
            postMessage(seq[1], seq[2]);
        }

        if(l.length() == 1){
            int i = Integer.parseInt(l);
            switch(i){
                case 0:
                    preFrame.setVisible(true);
                    errorLabel.setVisible(true);
                break;
                case 1:
                    display();
                break;
                default:
                break;
            }
        }

    }

    public void updateUsersList(String action, String user){
    	
        if(action.equals("ADD")){
            this.connectedUsers.add(user);
        }else if(action.equals("DEL")){
            this.connectedUsers.remove(this.connectedUsers.indexOf(user));
        }else{
            //Not parsed
        }
        //Borrar i reescriure
        
        this.usersList.setText("<"+this.username+"> (Me)\n");
        for(String name: connectedUsers){
    	    this.usersList.append("<"+name+">\n");
        }
    }

    public void postMessage(String usrName, String msg){
        this.chatList.append("<"+usrName+">: " + msg + "\n");
    }

	public void preDisplay(InetAddress chatURL, int chatPort) {

        newFrame.setVisible(false);
        preFrame = new JFrame(appName);
        
        usernameChooser = new JTextField(15);
        
        chooseUsernameLabel = new JLabel("Quin nickname vols tenir?");
        
        errorLabel = new JLabel("Error: Aquest nickname ja està en ús");
        errorLabel.setVisible(false);
        errorLabel.setForeground(Color.red);

        enterServerButton = new JButton("Entrar al chat");


        this.client = new Client(chatURL, chatPort);
        this.client.addObserver(this);
        enterServerButton.addActionListener(new enterServerButtonListener(this.client));
        
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(50, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        preRight.weightx = 2.0;
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(50, 10, 0, 10);

        prePanel.add(chooseUsernameLabel, preLeft);
        prePanel.add(usernameChooser, preRight);

        preFrame.add(BorderLayout.NORTH, prePanel);
        preFrame.add(BorderLayout.CENTER, errorLabel);
        preFrame.add(BorderLayout.SOUTH, enterServerButton);
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

        sendMessage = new JButton("Envia Missatge");
        sendMessage.addActionListener(new sendMessageButtonListener(this.client));

        chatList = new JTextArea();
        chatList.setEditable(false);
        chatList.setFont(new Font("Serif", Font.PLAIN, 15));
        chatList.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatList), BorderLayout.CENTER);

        usersList = new JTextArea();
        usersList.setEditable(false);
        usersList.setFont(new Font("Serif", Font.PLAIN, 15));
        usersList.setLineWrap(true);
        usersList.append("<" + username + "> (Me)\n");

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
        Client c;
        public sendMessageButtonListener(Client client){
            this.c = client;
        }
        public void actionPerformed(ActionEvent event) {
        
            String text = messageBox.getText();
            this.c.writeToSocket(text);
            messageBox.setText("");

        }
    }

    String  username;

    class enterServerButtonListener implements ActionListener {

        Client c;

        public enterServerButtonListener(Client client){
            this.c = client;
        }

        public void actionPerformed(ActionEvent event) {
            username = usernameChooser.getText();
            System.out.println(username);
            if (username.length() < 1) {
                System.out.println("No!");
            } else {
                preFrame.setVisible(false);
                this.c.writeToSocket(username);
            }
        }

    }
}