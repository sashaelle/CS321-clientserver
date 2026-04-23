/***********************************************************/
/* Author: Joy Janney, [Sasha Crawford]                    */
/* Major: Computer Science                                 */
/* Creation Date: March 25th, 2026                         */
/* Due Date: April 2nd, 2026                               */
/* Course: CS321-01                                        */
/* Professor Name: Professor Shimkanon                     */
/* Assignment: Final Project Phase #2                      */
/* Filename: blackjackClient.java                          */
/* Purpose: The server is meant to deal with user input    */
/*          for the blackjack game. Primary                */
/*          responsiblities are hit and hold for user and  */
/*          communicating that with the server to recieve  */
/*          the proper cards.                              */
/***********************************************************/

/* TO DO:
    * comment
 */

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
        int dealerScore;
        int playerScore;
        //for getting input from user
        Scanner std_in = new Scanner(System.in);
        String newCard;

        int option;//user option input
        boolean continueGame = true;//whether to start a new game or not

        //creating the card hashmaps for the user to know what card
        //we are not sending full card name which will be confusing for the user
        createHashMaps();

        //starting a blackjack gam
        System.out.println("Welcome to Blackjack");
        while(continueGame){

            //asking server to deal a new hand
            output.write("Deal\n", 0, 5);
            output.flush();
            dealerScore = r.nextInt(21);
            playerScore = r.nextInt(21);

            //new player and dealer hand for the game
            //new a new memory address each time
            ArrayList<String> playerHand = new ArrayList<String>();
            ArrayList<String> dealerHand = new ArrayList<String>();

            //do NOT move out, need new memory address
            //reading in the first two player cards
            newCard = input.readLine();
            addCard(playerHand, newCard);
            newCard = input.readLine();
            addCard(playerHand, newCard);

            //getting the two dealer cards
            newCard = input.readLine();
            addCard(dealerHand, newCard);
            newCard = input.readLine();
            addCard(dealerHand, newCard);

            //if the user wants to hit or hold
            boolean hit = true;
            
            //converting the hand into a numeric only for the scoring function and scoring
            playerScore = libblackjack.score(libblackjack.convertHand(playerHand));
            while(hit == true & playerScore < 21){

                //print out dealer hand
                System.out.println("**********");
                System.out.println("Dealer hand");
                System.out.println("Hidden card");
                //only printing out the second card since the first is hidden
                System.out.println(cardTranslator(dealerHand.get(1)));
                System.out.println("**********");

                //print out user hand
                System.out.println("**********");
                System.out.println("Your hand");
                for(String card : playerHand){
                    System.out.println(cardTranslator(card));
                }
                System.out.println("Score: " + playerScore);
                System.out.println("**********");

                //ask user to hit or hold
                boolean validInput = false;
                //while they input invalid options reprint options and ask for input
                while(!validInput){

                    //display options of hit or hold to user
                    System.out.println("(1) Hit\n(2) Hold");
                    //get the user selection
                    option = std_in.nextInt();

                    //if hit, ask server for new card
                    if (option == 1){
                        //communicate hit for server
                        output.write("Hit\n", 0, 4);
                        output.flush();

                        hit = true;//this is the for the game
                        validInput = true;//this is to get valid input

                        //getting a new card and calculating the new score
                        newCard = input.readLine();
                        addCard(playerHand, newCard);

                        //score hand
                        playerScore = libblackjack.score(libblackjack.convertHand(playerHand));
                    }

                    //if hold, end game
                    else if (option == 2){
                        //communicate hold to server
                        output.write("Hold\n", 0, 5);
                        output.flush();

                        //printing out the current dealer hand
                        System.out.println("Dealer hand: ");
                        System.out.println(cardTranslator(dealerHand.get(0)));
                        System.out.println(cardTranslator(dealerHand.get(1)));

                        //getting the dealer hand as it is dealed
                        //dealer picks up whilescore is 16 or les
                        while(dealerScore < 17){
                            //reading in card
                            newCard = input.readLine();
                            if(newCard == null){
                                System.out.println("No card given");
                            }

                            //adding card to hand and printing to user
                            addCard(dealerHand, newCard);
                            System.out.println(cardTranslator(newCard));

                            //updating to the scoring feature
                            dealerScore = libblackjack.score(libblackjack.convertHand(dealerHand));
                            
                        }
                        //score the hand just to make sure it is updated
                        dealerScore = libblackjack.score(libblackjack.convertHand(dealerHand));
                
                        hit = false;//indicates that the game is done
                        validInput = true;//indicates that the input was valid
                    }
                    else{
                        //tell user they had invalid input
                        System.out.println("Invalid Choice.");
                        validInput = false;
                    }
                }
                //TO DO: calculate score of the hand
            }

            //determining the winner
            //higher number not over 21
            if(playerScore > 21){
                System.out.println("Bust!");
            }
            else if(playerScore > dealerScore){
                System.out.println("Congrats you won!");
            }
            else{
                System.out.println("You lost!");
            }

            //print out scores of both hands
            System.out.println("Dealer hand score: " + dealerScore);
            System.out.println("Your hand score: " + playerScore);

            //ask user if they want to start a new game
            boolean validInput = false;
            while(!validInput){
                //ask user for input and get it
                System.out.println("(1) New Game\n(2) Exit");
                option = std_in.nextInt();

                //newgame
                if (option == 1){
                    continueGame = true;
                    validInput = true;
                    
                }
                //exit game
                else if (option == 2){
                    continueGame = false;
                    validInput = true;
                    output.write("Exit", 0, 4);
                    output.flush();
                }
                //invalid input
                else{
                    System.out.println("Invalid Choice.");
                    validInput = false;
                }
            }
        }  
        //close all connections 
        s.close();
        std_in.close();
        output.close();
        input.close();
        System.out.println("Connected closed to " + IP + " at port " + Port);
    }
    public static HashMap<String, String> numberNames = new HashMap<>();
    public static HashMap<String, String> suiteNames = new HashMap<>();
    public static void createHashMaps(){

        suiteNames.put("H", "Hearts");
        suiteNames.put("D", "Diamonds");
        suiteNames.put("C", "Clubs");
        suiteNames.put("S", "Spades");


        numberNames.put("2", "2");
        numberNames.put("3", "3");
        numberNames.put("4", "4");
        numberNames.put("5", "5");
        numberNames.put("6", "6");
        numberNames.put("7", "7");
        numberNames.put("8", "8");
        numberNames.put("9", "9");
        numberNames.put("10", "10");
        numberNames.put("J", "Jack");
        numberNames.put("Q", "Queen");
        numberNames.put("K", "King");
        numberNames.put("A", "Ace");
    }
    /****************************************************************************/
    /* Function name: cardTranslator                                            */
    /* Description: Takes a card from the <number>, <suite> formate and outputs */
    /*              it into a user friendly version                             */
    /* Parameters: String - card: the card of <number>, <suite> format          */
    /* Return Value: String - user friendly version of the card                 */
    /****************************************************************************/
    public static String cardTranslator(String card){
        //splitting on where the comma is since 10 is two chars but 1 is one char
        String[] cardSplit = card.split(",");

        //assigning the suite and number
        String suite = cardSplit[1].stripLeading();
        String number = cardSplit[0].stripLeading();

        //getting the 
        String nameSuite = suiteNames.get(suite);
        String nameNumber = numberNames.get(number);

        return nameNumber + " of " + nameSuite;
    }

    /****************************************************************************/
    /* Function name: addCard                                                   */
    /* Description: Adds to a hand from a given input                           */
    /* Parameters: ArrayList<String> - hand: the hand to save the card to       */
    /*              BufferedReader - input: the socket input                    */
    /* Return Value: void                                                       */
    /****************************************************************************/
    public static void addCard(ArrayList<String> hand, String newCard) throws Exception{
        //check if the suite and number were given by searching for the comma
        int index = newCard.indexOf(',');
        if(index <= 0){
            System.out.println("ERROR: " + newCard);
            return;
        }
        hand.add(newCard);
    }
}
