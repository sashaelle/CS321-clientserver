/*********************************************************/
/* Author: Sasha Crawford, [Joy Janney]                  */
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
import java.net.*;  
import java.util.*; 

public class blackjackServer {
    public static final int PORT = 1706; // Server will listen on this port

    /***************************************************************************/
    /* Function name: main()                                                   */
    /* Description: Main method to start the blackjack server                  */
    /* Parameters: None                                                        */
    /* Return Value: None                                                      */
    /***************************************************************************/
    public static void main(String[] args) throws IOException {
        // Check for correct usage
        if (args.length != 1) {
            System.err.println("Usage: java server 1706");
            System.exit(1);
        }  // end if usage clause

        // Create a server socket to listen for client connections
        int portNumber = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Server is listening on port " + portNumber);

        // Keep the server running to accept multiple client connections
        try {
            while (true) {
                // spawn a handler thread for client connection
                // Each client connection is handled in its own thread for concurrency
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
    private PrintWriter out; // output stream to send data to clients
    private BufferedReader in; // input stream to receive data from clients
    private String inputLine; // variable to store client input

    // Construct a handler thread
    public Handler(Socket socket) {
        this.socket = socket;
    }  // end Handler

    /***************************************************************************/
    /* Function name: run()                                                    */
    /* Description: runs the handler thread                                    */
    /* Parameters: None                                                        */
    /* Return Value: None                                                      */
    /***************************************************************************/
    public void run() {
        try {
            // Set up input and output streams for communication with the client
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read the initial input from the client
            inputLine = in.readLine();
            System.out.println("Client says: " + inputLine); // print the client's initial message to the console
            
            ArrayList<String> dealerHand = new ArrayList<>(); // initialize dealer's hand
            // Keep connection alive
            while (inputLine != null) {
                System.out.println("Client says: " + inputLine);

                /***************************************************************************/
                /* Expected client input:                                                  */
                /* Deal - deal a new hand                                                  */
                /* Hit - request another card                                              */
                /* Hold - keep the current hand                                            */
                /* Exit - quit the game                                                    */
                /***************************************************************************/

                if (inputLine.equalsIgnoreCase("Deal")) {
                    setCards(); // initialize the deck of cards

                    // Player's initial hand
                    int first = getRandomNum(); out.println(cards.get(first)); 
                    cards.remove(first); 
                    int second = getRandomNum(); out.println(cards.get(second)); 
                    cards.remove(second);

                    // Dealer's initial hand
                    int dealer1 = getRandomNum(); 
                    dealerHand.add(cards.get(dealer1)); out.println(dealerHand.get(0)); 
                    cards.remove(dealer1);
                    int dealer2 = getRandomNum(); 
                    dealerHand.add(cards.get(dealer2)); out.println(dealerHand.get(1)); 
                    cards.remove(dealer2);
                } else if (inputLine.equalsIgnoreCase("Hit")) { 
                    // Player chooses to hit, so we give them another card
                    int curr = getRandomNum(); out.println(cards.get(curr)); 
                    cards.remove(curr); 
                } else if (inputLine.equalsIgnoreCase("Hold")) {
                    // Player chooses to hold, so we play out the dealer's hand
                    // We calculate the dealer's score and determine if they need to hit or hold 
                    //      using convertHand and score functions from the libblackjack library
                    int dealerScore = libblackjack.score(libblackjack.convertHand(dealerHand)); // get the dealer's score
                    // Blackjack rule: Dealer must hit until they reach a score of at least 17
                    while (dealerScore < 17) { 
                        int curr = getRandomNum(); 
                        dealerHand.add(cards.get(curr)); out.println(cards.get(curr)); 
                        cards.remove(curr); 
                        dealerScore = libblackjack.score(libblackjack.convertHand(dealerHand)); 
                    }
                } else if (inputLine.equalsIgnoreCase("Exit")) {
                    // Player chooses to exit the game, so we end the connection
                    out.println("Goodbye!");
                    break;
                } else {
                    // If the client sends an unknown command, we notify them
                    out.println("Unknown command: " + inputLine);
                }
                inputLine = in.readLine(); // read the next input from the client
            }
        } catch (IOException e) {
            System.out.println(e); // catch any IO exceptions that may occur
        // Final clause to close the socket and end the connection
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

        /***************************************************************************/
        /* Function name: setCards                                                 */
        /* Description: Initializes the deck of cards                              */
        /* Parameters: None                                                        */
        /* Return Value: None                                                      */
        /***************************************************************************/
        public static void setCards() {
            cards.clear();
            for (String suit : suits) {
                for (String number : numbers) {
                    cards.add(number + ", " + suit);
                }
            }
        } // end function setCards

        /***************************************************************************/
        /* Function name: getRandomNum                                             */
        /* Description: Generates a random index for selecting a card              */
        /* Parameters: None                                                        */
        /* Return Value: None                                                      */
        /***************************************************************************/
        int getRandomNum() {
            return (int)(Math.random() * cards.size());
        }// end function getRandomNum
    }  // end class Handler
}