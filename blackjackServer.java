/*********************************************************/
/* Author: Sasha Crawford, [Joy Janney], [Youssef Ahmed] */
/* Major: Computer Science                               */
/* Creation Date: 03/24/2026                             */
/* Due Date: 04/24/2026                                  */
/* Course: CS-321 Communication and Networking           */
/* Professor Name: Professor Evan Shimkanon              */
/* Assignment: Client-Server Final Project               */
/* Filename: blackjackServer.java                        */
/* Purpose: Blackjack server implementation              */
/*********************************************************/

import java.io.*;
import java.lang.reflect.Array;
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
    private String inputLine;

    // Construct a handler thread
    public Handler(Socket socket) {
        this.socket = socket;
    }  // end Handler

    // do the thread processing
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            inputLine = in.readLine();
            System.out.println(inputLine);
            
            ArrayList<String> dealerHand = new ArrayList<>(); // initialize player's hand
            // Keep connection alive
            while (inputLine != null) {
                System.out.println("Client says: " + inputLine);

                if (inputLine.equalsIgnoreCase("Deal")) {
                    setCards(); // initialize the deck of cards
                    // Player's initial hand
                    int first = getRandomNum(); out.println(cards.get(first)); 
                    cards.remove(first);
                    
                    int second = getRandomNum(); out.println(cards.get(second)); 
                    cards.remove(second);

                    // Dealer's initial hand
                    int dealer1 = getRandomNum(); 
                    dealerHand.add(cards.get(dealer1)); out.println(dealerHand.get(0)); // store the dealer's hand in variables
                    cards.remove(dealer1);

                    int dealer2 = getRandomNum(); 
                    dealerHand.add(cards.get(dealer2)); out.println(dealerHand.get(1)); // store the dealer's hand in variables  
                    cards.remove(dealer2);
                } else if (inputLine.equalsIgnoreCase("Hit")) {
                    int curr = getRandomNum(); out.println(cards.get(curr)); // get a random card
                    cards.remove(curr); // remove the card from the deck
                } else if (inputLine.equalsIgnoreCase("Hold")) {
                    int dealer_hand = libblackjack.score(deal1.substring(0, deal1.indexOf(',')) + ","
                    + deal2.substring(0, deal2.indexOf(',')) + ",");
                    System.out.println("Dealer's hand: " + deal1 + ", " + deal2 + " with score " + dealer_hand);
                    while (dealer_hand < 17) {
                        int curr = getRandomNum(); // get a random card
                        out.println(cards.get(curr)); 
                        String dealerNewCard = cards.get(curr);
                        
                        dealer_hand = libblackjack.score(deal1.substring(0, deal1.indexOf(',')) + ","
                         + deal2.substring(0, deal2.indexOf(',')) + ","
                         + dealerNewCard.substring(0, dealerNewCard.indexOf(','))); // update the dealer's hand score
                        cards.remove(curr); // remove the card from the deck
                    }
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
        private static String[] suits = {"H", "D", "C", "S"};
        private static String[] numbers = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        public static ArrayList<String> cards = new ArrayList<>();

        public static void setCards() {
            cards.clear();
            for (String suit : suits) {
                for (String number : numbers) {
                    cards.add(number + ", " + suit);
                }
            }
        }

        int getRandomNum() {
            return (int)(Math.random() * cards.size());
        }// end function getRandomNum
    }  // end class Handler
}