/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class chat_client implements Runnable {

    // Globals

    Socket SOCK;
    Scanner INPUT;
    Scanner SEND = new Scanner(System.in);
    PrintWriter OUT;

    public chat_client(Socket X) {
        this.SOCK = X;
    }

    @Override
    public void run() {
        try {
            try {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                OUT.flush();
                CheckStream();
            } finally {
                SOCK.close();
            }
        } catch (Exception X) {
            System.out.print(X);
        }
    }

    public void DISCONNECT() throws IOException {
        OUT.println(chat_client_gui.UserName + " est déconnecté.");
        OUT.flush();
        SOCK.close();
        JOptionPane.showMessageDialog(null, "Vous êtes déconnecté!");
        System.exit(0);
    }

    public void CheckStream() {
        while (true) {
            RECEIVE();
        }
    }

    public void RECEIVE() {
        if (INPUT.hasNext()) {
            String MESSAGE = INPUT.nextLine();

            if (MESSAGE.contains("#?!")) {
                String TEMP1 = MESSAGE.substring(3);
                TEMP1 = TEMP1.replace("[", "");
                TEMP1 = TEMP1.replace("]", "");

                String[] CurrentUsers = TEMP1.split(", ");

                chat_client_gui.JL_ONLINE.setListData(CurrentUsers);
            } else {
                chat_client_gui.TA_CONVERSATION.append(MESSAGE + "\n");
            }
        }
    }

    public void SEND(String X) {
        OUT.println(chat_client_gui.UserName + ": " + X);
        OUT.flush();
        chat_client_gui.TF_Message.setText("");
    }
}
