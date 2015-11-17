/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class chat_server {
    public static ArrayList<Socket> ConnectionArray = new ArrayList<>();
    public static ArrayList<String> CurrentUsers = new ArrayList<>();
    public static ServerSocket serverSocket = null;
    public static final int PORT = 2009;

    public static void main(String[] args) throws IOException {
        try {
            //initialisation du Server Socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("En attente de connexions...");

            while (true) {
                //création de le connexion bidirectionnelle
                Socket SOCK = serverSocket.accept();
                ConnectionArray.add(SOCK);
                System.out.println("Le client s'est connecté depuis : " + SOCK.getLocalAddress().getHostName());

                //ajout du nom de l'utilisateur (utilisateur actif)
                AddUserName(SOCK);

                //ajout d'un thread pour le serveur pour chaque connexion
                chat_server_return CHAT = new chat_server_return(SOCK);
                Thread X = new Thread(CHAT);
                X.start();
            }
        } catch (Exception X) {
            System.out.print(X);
        }
    }

    public static void AddUserName(Socket X) throws IOException {
        //analyse du texte reçu et ajout de l'utilisateur
        Scanner INPUT = new Scanner(X.getInputStream());
        String UserName = INPUT.nextLine();
        CurrentUsers.add(UserName);

        //envoi à tout les utilisateurs le nouvel utilisateur
        for (int i = 1; i <= chat_server.ConnectionArray.size(); i++) {
            Socket TEMP_SOCK = (Socket) chat_server.ConnectionArray.get(i - 1);
            PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
            OUT.println("#?!" + CurrentUsers);
            OUT.flush();
        }
    }
}
