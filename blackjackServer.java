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
import java.util.*; 

public class blackjackServer {
    public static final int PORT = 1706; // Server will listen on this port

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java server 1706");
            System.exit(1);
        }  // end if usage clause

        int portNumber = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Server is listening on port " + portNumber);
        try {
            while (true) {
                // spawn a handler thread for client connection
                new Handler(serverSocket.accept()).start();
            }  // end while
        } finally {
            serverSocket.close();
        } // end finally
    }

    /*********************************************************
    * A handler thread class to work with a single client.
    *********************************************************/
    private static class Handler extends Thread {
    private Socket socket;   // socket to use to connect to clients
    private PrintWriter out;
    private BufferedReader in;
    private String inputLine, outputLine;

    // Construct a handler thread
    public Handler(Socket socket) {
        this.socket = socket;
    }  // end Handler

    // do the thread processing
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            /*
            // Send greeting when client connects
            out.println("Hello!");
            System.out.println("Hello from Server!");
            */

            inputLine = in.readLine();
            System.out.println(inputLine);
            
            // Keep connection alive
            while (inputLine != null) {
                System.out.println("Client says: " + inputLine);

                if (inputLine.equalsIgnoreCase("Deal")) {
                    out.println("7, H"); out.println("5, D");
                    out.print("dealer has hearts 8");
                } else if (inputLine.equalsIgnoreCase("Hit")) {
                    out.println("3, H");
                } else if (inputLine.equalsIgnoreCase("Hold")) {
                    out.println("Player holds. Dealer's turn.");
                } else if (inputLine.equalsIgnoreCase("Exit")) {
                    out.println("Goodbye!");
                    break;
                } else {
                    out.println("Unknown command: " + inputLine);
                }
                inputLine = in.readLine();
            }

        } catch (IOException e) {
            System.out.println(e);
        } finally {
                try {
                    socket.close();
                    System.out.println("Goodbye!");
                } catch (IOException e) {
                    System.out.println(e);  
                }
            } // end finally
        } // end function run
    }  // end class Handler
}