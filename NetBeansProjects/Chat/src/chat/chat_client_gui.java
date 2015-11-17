/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import javax.swing.*;
import java.io.PrintWriter;
import java.net.*;

public class chat_client_gui {

    // Globals

    private static chat_client ChatClient;
    public static String UserName = "Anonymous";

    // GUI Globals - Main Window
    public static JFrame MainWindow = new JFrame();
    private static JButton B_CONNECT = new JButton();
    private static JButton B_DISCONNECT = new JButton();
    private static JButton B_SEND = new JButton();
    private static JLabel L_Message = new JLabel("Message: ");
    public static JTextField TF_Message = new JTextField(20);
    private static JLabel L_Conversation = new JLabel();
    public static JTextArea TA_CONVERSATION = new JTextArea();
    private static JScrollPane SP_CONVERSATION = new JScrollPane();
    private static JLabel L_ONLINE = new JLabel();
    public static JList JL_ONLINE = new JList();
    private static JScrollPane SP_ONLINE = new JScrollPane();
    private static JLabel L_LoggedInAs = new JLabel();
    private static JLabel L_LoggedInAsBox = new JLabel();

    // GUI Globals - LogIn Window
    public static JFrame LogInWindow = new JFrame();
    public static JTextField TF_UserNameBox = new JTextField(20);
    private static JButton B_ENTER = new JButton("Valider");
    private static JLabel L_EnterUserName = new JLabel("Entrez Login: ");
    private static JPanel P_LogIn = new JPanel();

    public static void main(String args[]) {
        BuildMainWindow();
        Initialize();
    }

    public static void Connect() {
        try {
            final int PORT = 2009;
            final InetAddress HOST = InetAddress.getLocalHost();
            Socket SOCK = new Socket(HOST, PORT);
            System.out.println("Connexion à : " + HOST);

            ChatClient = new chat_client(SOCK);

            PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());
            OUT.println(UserName);
            OUT.flush();

            Thread X = new Thread(ChatClient);
            X.start();
        } catch (Exception X) {
            System.out.print(X);
            JOptionPane.showMessageDialog(null, "Le serveur de répond pas.");
            System.exit(0);
        }
    }

    public static void Initialize() {
        B_SEND.setVisible(false);
        B_DISCONNECT.setVisible(false);
        B_CONNECT.setVisible(true);
    }

    public static void BuildMainWindow() {
        MainWindow.setTitle("Fenetre de chat de " + UserName);
        MainWindow.setSize(450, 500);
        MainWindow.setLocation(220, 180);
        MainWindow.setResizable(false);
        ConfigureMainWindow();
        MainWindow_Action();
        MainWindow.setVisible(true);
    }

    public static void BuildLogInWindow() {
        LogInWindow.setTitle("Qui êtes vous?");
        LogInWindow.setSize(400, 100);
        LogInWindow.setLocation(250, 200);
        LogInWindow.setResizable(false);
        P_LogIn = new JPanel();
        P_LogIn.add(L_EnterUserName);
        P_LogIn.add(TF_UserNameBox);
        P_LogIn.add(B_ENTER);
        LogInWindow.add(P_LogIn);

        Login_Action();
        LogInWindow.setVisible(true);
    }

    public static void ConfigureMainWindow() {
        MainWindow.setBackground(new java.awt.Color(255, 255, 255));
        MainWindow.setSize(500, 320);
        MainWindow.getContentPane().setLayout(null);

        B_SEND.setText("Envoyer");
        MainWindow.getContentPane().add(B_SEND);
        B_SEND.setBounds(250, 40, 81, 25);

        B_DISCONNECT.setText("Déconnecter");
        MainWindow.getContentPane().add(B_DISCONNECT);
        B_DISCONNECT.setBounds(10, 40, 110, 25);

        B_CONNECT.setText("Connexion");
        B_CONNECT.setToolTipText("");
        MainWindow.getContentPane().add(B_CONNECT);
        B_CONNECT.setBounds(130, 40, 110, 25);

        L_Message.setText("Message:");
        MainWindow.getContentPane().add(L_Message);
        L_Message.setBounds(10, 10, 60, 20);

        TF_Message.setForeground(new java.awt.Color(0, 0, 255));
        TF_Message.requestFocus();
        MainWindow.getContentPane().add(TF_Message);
        TF_Message.setBounds(70, 4, 260, 30);

        L_Conversation.setHorizontalAlignment(SwingConstants.CENTER);
        L_Conversation.setText("Conversation");
        MainWindow.getContentPane().add(L_Conversation);
        L_Conversation.setBounds(100, 70, 140, 16);

        TA_CONVERSATION.setColumns(20);
        TA_CONVERSATION.setFont(new java.awt.Font("Tahoma", 0, 12));
        TA_CONVERSATION.setForeground(new java.awt.Color(0, 0, 255));
        TA_CONVERSATION.setLineWrap(true);
        TA_CONVERSATION.setRows(5);
        TA_CONVERSATION.setEditable(false);

        SP_CONVERSATION.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_CONVERSATION.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_CONVERSATION.setViewportView(TA_CONVERSATION);
        MainWindow.getContentPane().add(SP_CONVERSATION);
        SP_CONVERSATION.setBounds(10, 90, 330, 180);

        L_ONLINE.setHorizontalAlignment(SwingConstants.CENTER);
        L_ONLINE.setText("En ligne");
        L_ONLINE.setToolTipText("");
        MainWindow.getContentPane().add(L_ONLINE);
        L_ONLINE.setBounds(350, 70, 130, 16);

        JL_ONLINE.setForeground(new java.awt.Color(0, 0, 255));

        SP_ONLINE.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_ONLINE.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_ONLINE.setViewportView(JL_ONLINE);
        MainWindow.getContentPane().add(SP_ONLINE);
        SP_ONLINE.setBounds(350, 90, 130, 180);

        L_LoggedInAs.setFont(new java.awt.Font("Tahoma", 0, 12));
        L_LoggedInAs.setText("Vous êtes :");
        MainWindow.getContentPane().add(L_LoggedInAs);
        L_LoggedInAs.setBounds(348, 0, 140, 15);

        L_LoggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
        L_LoggedInAsBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        L_LoggedInAsBox.setForeground(new java.awt.Color(255, 0, 0));
        L_LoggedInAsBox.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MainWindow.getContentPane().add(L_LoggedInAsBox);
        L_LoggedInAsBox.setBounds(340, 17, 150, 20);
    }

    public static void Login_Action() {
        B_ENTER.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        ACTION_B_ENTER();
                    }
                }
        );
    }

    public static void ACTION_B_ENTER() {
        if (!TF_UserNameBox.getText().equals("")) {
            UserName = TF_UserNameBox.getText().trim();
            L_LoggedInAsBox.setText(UserName);
            chat_server.CurrentUsers.add(UserName);
            MainWindow.setTitle("Fenetre de chat de " + UserName);
            LogInWindow.setVisible(false);
            B_SEND.setVisible(true);
            B_DISCONNECT.setVisible(true);
            B_CONNECT.setVisible(false);
            Connect();
        } else {
            JOptionPane.showMessageDialog(null, "Entrez votre nom!");
        }
    }

    public static void MainWindow_Action() {
        B_SEND.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        ACTION_B_SEND();
                    }
                }
        );

        B_CONNECT.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        BuildLogInWindow();
                    }
                }
        );

        B_DISCONNECT.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        ACTION_B_DISCONNECT();
                    }
                }
        );
    }

    public static void ACTION_B_SEND() {
        if (!TF_Message.getText().equals("")) {
            ChatClient.SEND(TF_Message.getText());
            TF_Message.requestFocus();
        }
    }

    public static void ACTION_B_DISCONNECT() {
        try {
            ChatClient.DISCONNECT();
        } catch (Exception Y) {
            Y.printStackTrace();
        }
    }
}
