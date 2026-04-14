/***********************************************************/
/* Author: Joy Janney                                      */
/* Major: Computer Science                                 */
/* Creation Date: March 25th, 2026                         */
/* Due Date: April 2nd, 2026                               */
/* Course: CS321-01                                        */
/* Professor Name: Professor Shimkanon                     */
/* Assignment: Final Project Phase #1                      */
/* Filename: blackjackClient.java                          */
/* Purpose: This program connects to a blackjack server,   */
/*   says hello, disconnects, and says goodbye.            */
/***********************************************************/

/* TO DO:
    * accept input from the server
    * comment
    * use score function
    * add in initial 2 card
    * add in dealer hand
 */

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.*;
import java.lang.String;
import java.io.OutputStream;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.io.DataOutputStream;
import java.io.DataInputStream;


public class blackjackClient{
    //details for the socket connection if static (no command line arguments)
    final public static int PORT = 1706;
    final public static String IP = "localhost";
   
    public static void main(String[] args) throws Exception
    {
        //checking the command line arguments
        if(args.length!= 2){
            System.out.println("usage: <hostname> <IP address>");
        }

        //setting up the socket
        int Port = Integer.parseInt(args[1]);
        String IP = args[0];
        Socket s = new Socket(IP, Port);
        System.out.println("Connected opened to " + IP + " at port " + Port);
        DataOutputStream output = new DataOutputStream(s.getOutputStream());
        DataInputStream input = new DataInputStream(s.getInputStream());

        Random r = new Random();
        Scanner std_in = new Scanner(System.in);

        int option;//user option input
        boolean continueGame = true;//whether to start a new game or not
        createHashMaps();

        System.out.println("Welcome to Blackjack");
        while(continueGame){
            //asking server to deal a new hand
            output.writeChars("Deal");

            //hand for the game
            ArrayList<char[]> hand = new ArrayList<char[]>();
            System.out.println("Waiting on intial 2 cards from server");
            //do NOT move out, need new memory address
            char[] initialCard1 = {'X', 'X'};
            initialCard1[0] = input.readChar();
            initialCard1[1] = input.readChar();
            //add card to hand
            hand.add(initialCard1);
            //do NOT move out, need new memory address
            char[] initialCard2 = {'X', 'X'};
            initialCard2[0] = input.readChar();
            initialCard2[1] = input.readChar();
            //add card to hand
            hand.add(initialCard2);

            //whether to hit or hold
            boolean hit = true;
            //continue while they want to hit
            //TO DO: compare to score
            while(hit == true){

                //print out user hand
                for(char[] card : hand){
                    System.out.println(cardTranslator(card));
                }
                //TO DO: print out dealer hand

                //ask user to hit or hold
                boolean validInput = false;
                while(!validInput){
                    System.out.println("(1) Hit\n(2) Hold");
                    option = std_in.nextInt();
                    if (option == 1){
                        output.write(1);
                        hit = true;
                        validInput = true;
                         //do NOT move out, need new memory address
                         System.out.println("Waiting on new card from server");
                        char[] newCard = {'X', 'X'};
                        newCard[0] = input.readChar();
                        newCard[1] = input.readChar();
                        //add card to hand
                        hand.add(newCard);
                    }
                    else if (option == 2){
                        output.write(0);
                        hit = false;
                        validInput = true;
                    }
                    else{
                        System.out.println("Invalid Choice.");
                        validInput = false;
                    }
                }
                //TO DO: calculate score of the hand
            }
            //TO DO: call score function to determine winner
            if(r.nextInt(2) == 0){
                System.out.println("Congrats you won!!!");
                System.out.println("Dealer hand score: " + (r.nextInt(10)));
                System.out.println("Your hand score: " + (r.nextInt(10) + 11));
            }
            else{
                System.out.println("Better luck next time :(");
                System.out.println("Dealer hand score: " + (r.nextInt(10) + 11));
                System.out.println("Your hand score: " + r.nextInt(10));
            }

            //ask user if they want to start a new game
            boolean validInput = false;
            while(!validInput){
                System.out.println("(1) New Game\n(2) Exit");

                option = std_in.nextInt();
                if (option == 1){
                    continueGame = true;
                    validInput = true;
                    output.writeChars("Deal");
                    
                }
                else if (option == 2){
                    continueGame = false;
                    validInput = true;
                    output.writeChars("Exit");
                }
                else{
                    System.out.println("Invalid Choice.");
                    validInput = false;
                }
            }
        }   
        //https://www.geeksforgeeks.org/java/socket-programming-in-java/
        DataOutputStream socketOutput = new DataOutputStream(s.getOutputStream());
        socketOutput.writeUTF("Goodbye");
        s.close();
        std_in.close();
        output.close();
        input.close();
        System.out.println("Connected closed to " + IP + " at port " + Port);
    }
    public static HashMap<Character, String> numberNames = new HashMap<>();
    public static HashMap<Character, String> suiteNames = new HashMap<>();
    public static void createHashMaps(){

        suiteNames.put('H', "Hearts");
        suiteNames.put('D', "Diamonds");
        suiteNames.put('C', "Clubs");
        suiteNames.put('S', "Spades");


        numberNames.put('2', "2");
        numberNames.put('3', "3");
        numberNames.put('4', "4");
        numberNames.put('5', "5");
        numberNames.put('6', "6");
        numberNames.put('7', "7");
        numberNames.put('8', "8");
        numberNames.put('9', "9");
        numberNames.put('J', "Jack");
        numberNames.put('Q', "Queen");
        numberNames.put('K', "King");
        numberNames.put('A', "Ace");
    }
    //this is translating the protocol names of the cards to the common human names
    public static String cardTranslator(char[] cardName){
        char suite = cardName[0];
        char number = cardName[1];

        String nameSuite = suiteNames.get(suite);
        String nameNumber = numberNames.get(number);


        return nameNumber + " of " + nameSuite;
    }
}
