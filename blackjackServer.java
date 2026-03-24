/*********************************************************/
/* Author: Sasha Crawford, [Joy Janney], [Youssef Ahmed] */
/* Major: Computer Science                               */
/* Creation Date: 03/24/2026                             */
/* Due Date: 04/02/2026                                  */
/* Course: CS-321 Communication and Networking           */
/* Professor Name: Professor Evan Shimkanon              */
/* Assignment: Client-Server Final Project               */
/* Filename: blackjackServer.java                        */
/* Purpose: Blackjack server implementation              */
/*********************************************************/

import java.io.*;
import java.net.*;  

public class blackjackServer {
    public static final int PORT = 1706; // Server will listen on this port

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Blackjack Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Hello!");

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}