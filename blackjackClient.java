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
    * add option to continue game infinitely
    * accept input from the server
    * comment
    * use score function
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


public class blackjackClient{
   //details for the socket connection if static (no command line arguments)
    final public static int PORT = 1706;
    final public static String IP = "localhost";
   
    public static void main(String[] args) throws Exception
    {
        int Port = Integer.parseInt(args[1]);
        String IP = args[0];
        Socket s = new Socket(IP, Port);
        boolean hit = true;
        ArrayList<char[]> hand = new ArrayList<char[]>();

        /*InputStream in = s.getInputStream();
        byte [] buf = new byte[1024];
        int n = in.read(buf);
        System.out.println(new String(buf, 0, n));*/

        System.out.println("Connected opened to " + IP + " at port " + Port);
        Random r = new Random();

        System.out.println("Welcome to Blackjack");
        int rand;
        Scanner std_in = new Scanner(System.in);
        while(hit == true){
            //do NOT move out, need new memory address
            char[] newCard = {'X','X'};
            //change to accept from server
            rand = r.nextInt(4);
            if (rand == 0)
                newCard[0] = 'H';
            else if (rand == 1)
                newCard[0] = 'D';
            else if (rand == 2)
                newCard[0] = 'C';
            else
                newCard[0] = 'S';
            
            rand = r.nextInt(13);
            System.out.println("$$$$^^^" + rand);
            if ((rand >= 2) & (rand < 10)){
                char c = Integer.toString(rand).charAt(0);
                newCard[1] = c;
                System.out.println("$$$$" + c + "&&&&");
            }
            else if (rand == 0){
                newCard[1] = 'A';
            }
            else if (rand == 1){
                newCard[1] = 'K';
            }
            else if (rand == 11){
                newCard[1] = 'Q';
            }
            else{
                newCard[1] = 'J';
            }
            System.out.println("****" + newCard[1]);
            System.out.println("**" + newCard[0] + newCard[1] + "**");
            hand.add(newCard);
            System.out.println("Your hand is " + hand);
            for(char[] card : hand){
                System.out.println(cardTranslator(card));
            }

            System.out.println("(1) Hit\n(2) Hold");
            if (std_in.nextInt() == 1)
                hit = true;
            else
                hit = false;
            //check if over before allowing to continue
        }
        std_in.close();
        //call score function
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
        
        //https://www.geeksforgeeks.org/java/socket-programming-in-java/
        DataOutputStream socketOutput = new DataOutputStream(s.getOutputStream());
        socketOutput.writeUTF("Goodbye");
        s.close();
        System.out.println("Connected closed to " + IP + " at port " + Port);
    }
    public static String cardTranslator(char[] cardName){
        char suite = cardName[0];
        char number = cardName[1];

        HashMap<Character, String> suiteNames = new HashMap<>();
        suiteNames.put('H', "Hearts");
        suiteNames.put('D', "Diamonds");
        suiteNames.put('C', "Clubs");
        suiteNames.put('S', "Spades");

        HashMap<Character, String> numberNames = new HashMap<>();
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

        String nameSuite = suiteNames.get(suite);
        String nameNumber = numberNames.get(number);


        return nameNumber + " of " + nameSuite;
    }
}
