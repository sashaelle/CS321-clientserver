/***********************************************************/
/* Author: Joy Janney                                      */
/* Major: Computer Science                                 */
/* Creation Date: March 25th, 2026                         */
/* Due Date: April 2nd, 2026                               */
/* Course: CS321-01                                        */
/* Professor Name: Professor Shimkanon                     */
/* Assignment: Final Project Phase #2                      */
/* Filename: blackjackClient.java                          */
/* Purpose: This program connects to a blackjack server,   */
/*   says hello, disconnects, and says goodbye.            */
/***********************************************************/

/* TO DO:
    * comment
    * use score function
    * add in dealer hand
 */

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.lang.String;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.io.BufferedReader;

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

        //setting up reading and writing from the socket
        OutputStreamWriter output = new OutputStreamWriter(s.getOutputStream(), "UTF8");
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF8"));

        //get rid of r when score funciton is implemented
        Random r = new Random();
        int playerScore = r.nextInt(25);
        int dealerScore = r.nextInt(20);

        //for getting input from user
        Scanner std_in = new Scanner(System.in);

        int option;//user option input
        boolean continueGame = true;//whether to start a new game or not

        //should have functions moved to library later
        createHashMaps();

        System.out.println("Welcome to Blackjack");
        while(continueGame){
            //asking server to deal a new hand
            output.write("Deal\n", 0, 5);
            output.flush();

            //new player hand for the game
            ArrayList<char[]> playerHand = new ArrayList<char[]>();
            ArrayList<char[]> dealerHand = new ArrayList<char[]>();
            System.out.println("Waiting on intial 2 cards from server");
            //do NOT move out, need new memory address
            addCard(playerHand, input);
            addCard(playerHand, input);

            addCard(dealerHand, input);

            //whether to hit or hold
            boolean hit = true;
            //continue while they want to hit
            //TO DO: compare to score
            while(hit == true & playerScore < 21){

                //print out user hand
                System.out.println("**********");
                System.out.println("Your hand");
                for(char[] card : playerHand){
                    System.out.println(cardTranslator(card));
                }
                System.out.println("**********");

                //print out dealer hand
                System.out.println("**********");
                System.out.println("Dealer hand");
                System.out.println("Hidden card");
                System.out.println(cardTranslator(dealerHand.get(0)));
                System.out.println("**********");

                //ask user to hit or hold
                boolean validInput = false;
                while(!validInput){
                    System.out.println("(1) Hit\n(2) Hold");
                    option = std_in.nextInt();

                    //if hit, ask server for new card
                    if (option == 1){
                        output.write("Hit\n", 0, 4);
                        output.flush();
                        hit = true;
                        validInput = true;
                         //do NOT move out, need new memory address
                        System.out.println("Waiting on new card from server");
                        addCard(playerHand, input);
                    }

                    //if hold, end game
                    else if (option == 2){
                        output.write("Hold\n", 0, 5);
                        output.flush();
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
            if(playerScore > 21){
                System.out.println("Bust!");
            }
            else if(playerScore > dealerScore){
                System.out.println("Congrats you won!");
            }
            else{
                System.out.println("You lost!");
            }
            System.out.println("Dealer hand score: " + dealerScore);
            System.out.println("Your hand score: " + playerScore);

            //ask user if they want to start a new game
            boolean validInput = false;
            while(!validInput){
                System.out.println("(1) New Game\n(2) Exit");

                option = std_in.nextInt();
                if (option == 1){
                    continueGame = true;
                    validInput = true;
                    //output.print("Deal");
                    
                }
                else if (option == 2){
                    continueGame = false;
                    validInput = true;
                    output.write("Exit", 0, 4);
                    output.flush();
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
        char suite = cardName[1];
        char number = cardName[0];

        String nameSuite = suiteNames.get(suite);
        String nameNumber = numberNames.get(number);


        return nameNumber + " of " + nameSuite;
    }

    /****************************************************************************/
    /* Function name: addCard                                                   */
    /* Description: Adds to a hand from a given input                           */
    /* Parameters: ArrayList<char[]> - hand: the hand to save the card to       */
    /*              InputStreamReader input: the socket input                   */
    /* Return Value: void                                                       */
    /****************************************************************************/
    public static void addCard(ArrayList<char[]> hand, BufferedReader input) throws Exception{

            String inString;
            inString = input.readLine();
            System.out.println("Input: " + inString);

            int index = inString.indexOf(',');
            if(index <= 0){
                System.out.println("ERROR: " + inString);
                return;
            }
            //add card to hand
            char[] card = {inString.charAt(index-1), inString.charAt(index+2)};
            hand.add(card);
    }
}
