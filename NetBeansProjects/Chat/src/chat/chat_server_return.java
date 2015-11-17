/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class chat_server_return implements Runnable {

    // Globals
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";

    public chat_server_return(Socket X) {
        this.SOCK = X;
    }

    public void CheckConnection() throws IOException {
        if (!SOCK.isConnected()) {
            //suppression de la connexion de l'array list
            for (int i = 1; i <= chat_server.ConnectionArray.size(); i++) {
                if (chat_server.ConnectionArray.get(i) == SOCK) {
                    chat_server.ConnectionArray.remove(i);
                }
            }

            //envoi de l'information : déconnecté
            for (int i = 1; i <= chat_server.ConnectionArray.size(); i++) {
                Socket TEMP_SOCK = (Socket) chat_server.ConnectionArray.get(i - 1);
                PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                TEMP_OUT.println(TEMP_SOCK.getLocalAddress().getHostName() + " déconnecté!");
                TEMP_OUT.flush();
                // Show disconnection at SERVER
                System.out.println(TEMP_SOCK.getLocalAddress().getHostName() + " déconnecté!");
            }
        }
    }

    public void run() {
        try {
            try {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());

                while (true) {
                    CheckConnection();

                    if (!INPUT.hasNext()) {
                        return;
                    }

                    //récupération du message
                    MESSAGE = INPUT.nextLine();
                    System.out.println(MESSAGE);

                    //envoi du message à tout le monde
                    for (int i = 1; i <= chat_server.ConnectionArray.size(); i++) {
                        Socket TEMP_SOCK = (Socket) chat_server.ConnectionArray.get(i - 1);
                        PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println(MESSAGE);
                        TEMP_OUT.flush();
                        System.out.println("Envoyé à : " + TEMP_SOCK.getLocalAddress().getHostName());
                    }
                }
            } finally {
                //fermeture de la connexion
                SOCK.close();
            }
        } catch (Exception X) {
            System.out.print(X);
        }
    }
}
