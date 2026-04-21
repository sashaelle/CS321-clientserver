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
            
            //int randomNum = 

            // Keep connection alive
            while (inputLine != null) {
                System.out.println("Client says: " + inputLine);

                if (inputLine.equalsIgnoreCase("Deal")) {
                    setCards(); // initialize the deck of cards
                    // Player's initial hand
                    int first = getRandomNum(); // get a random card
                    out.println(cards.get(first)); 
                    System.out.println("Player's initial hand: " + cards.get(first));
                    cards.remove(first); // remove the card from the deck

                    int second = getRandomNum(); // get another random card
                    out.println(cards.get(second)); 
                    System.out.println("Player's initial hand: " + cards.get(second));
                    cards.remove(second); // remove the card from the deck

                    // Dealer's initial hand
                    int dealer = getRandomNum(); // get another random card for dealer 
                    out.println(cards.get(dealer)); 
                    System.out.println("Dealer's initial hand: " + cards.get(dealer));
                    cards.remove(dealer); // remove the card from the deck

                    System.out.println("Deck size after dealing: " + cards.size());
                } else if (inputLine.equalsIgnoreCase("Hit")) {
                    int curr = getRandomNum(); // get a random card
                    out.println(cards.get(curr)); 
                    System.out.println("Player's hit: " + cards.get(curr));
                    cards.remove(curr); // remove the card from the deck

                    System.out.println("Deck size after hit: " + cards.size());
                } else if (inputLine.equalsIgnoreCase("Hold")) {
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

        // HashMap to represent the deck of cards
        public static HashMap<Integer, String> cards = new HashMap<>();
        private static String[] suits = {"H", "D", "C", "S"};
        private static String[] numbers = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        public static void setCards(){
            int id = 0;
            for (String suit : suits) {
                for (String number : numbers) {
                    cards.put(id, number+", "+suit);
                    id++;
                }
            }
        } // end function setCards

        int getRandomNum(){
            return (int)(Math.random() * (cards.size() + 1));
        } // end function getRandomNum
    }  // end class Handler
}