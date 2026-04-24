/***********************************************************/
/* Author: Joy Janney & Sasha Crawford                     */
/* Major: Computer Science                                 */
/* Creation Date: April 21st, 2025                         */
/* Due Date: April 23nd, 2026                              */
/* Course: CS321-01                                        */
/* Professor Name: Professor Shimkanon                     */
/* Assignment: Final Project Phase #2                      */
/* Filename: blackjackClient.java                          */
/* Purpose: The library holds functions that the client    */
/*          and server use in common.                      */
/***********************************************************/

import java.util.ArrayList;
import java.util.HashMap;

class libblackjack{
    /****************************************************************************/
    /* Function name: score                                                     */
    /* Description: Takes in a hand and return the score of the hand            */
    /* Parameters: String - hand: numbers of the hand followed by commas        */
    /* Return Value: int - the score of the hand                                */
    /****************************************************************************/
    public static int score(String hand) {
        //the values of the cards
        HashMap<String, Integer> scores = new HashMap<String, Integer>();
        scores.put("2", 2);
        scores.put("3", 3);
        scores.put("4", 4);
        scores.put("5", 5);
        scores.put("6", 6);
        scores.put("7", 7);
        scores.put("8", 8);
        scores.put("9", 9);
        scores.put("10", 10);
        scores.put("J", 10);
        scores.put("Q", 10);
        scores.put("K", 10);
        scores.put("A", 1);

        int num_aces = 0;
        int score = 0;

        //splitting the hand based on the commas
        String[] split_hand = hand.split(",");
        for (String card : split_hand) {
            //if its an ace that is calculated at the end
            if (card.equals("A")) {
                num_aces += 1;
            }
            //adding the score to the hand
            else {
                score += scores.get(card);
            }
        }
        
        //at max one ace at value 11 can be added
        //if are other aces, check if all the values push it over 21
        if (((num_aces - 1 + 11 + score) <= 21) & (num_aces > 0)){
            score += num_aces - 1 + 11;
        }
        //add all the aces in as one
        else{
            score += num_aces;
        }

        return score;
    }

    /****************************************************************************/
    /* Function name: convertHand.                                              */
    /* Description: Takes a hand from the <number>, <suite> format and outputs  */
    /*              it into a <number>,<number>, etc. format                    */
    /* Parameters: ArrayList<String> - hand: the hand of <number>, <suite>      */
    /*              format                                                      */
    /* Return Value: String - string of <number>,<number>, etc. format          */
    /****************************************************************************/
    public static String convertHand(ArrayList<String> hand){
        String stringHand = "";

        //for each card only take the number and append it onto the string
        for(String card : hand){
            //split to only get the number
            String[] num = card.split(",");
            //concat to the end of the string
            stringHand = stringHand.concat(num[0] + ",");
        }
        return stringHand.substring(0, stringHand.length()-1);
    }
}