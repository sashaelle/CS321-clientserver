//necessary imports
import java.net.Socket;
import java.io.InputStream;

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

public class blackjackClient{
   //details for the socket connection if static (no command line arguments)
    final public static int PORT = 1706;
    final public static String IP = "localhost";
   
    public static void main(String[] args) throws Exception
    {
       //creating the socket
        Socket s = new Socket(IP, PORT);
       
       //use is you want to read in from the server
        /*InputStream in = s.getInputStream();
        byte [] buf = new byte[1024];
        int n = in.read(buf);
        System.out.println(new String(buf, 0, n));*/

       //printing out socket connection details
        System.out.println("Socket Created");

       //timing out socket in 10 seconds
        System.out.println("Closing socket connection in 10 seconds...");
        //https://medium.com/@AlexanderObregon/building-a-countdown-timer-in-java-using-loops-for-beginners-67db5d461c18
        for(int i = 9; i > 0; i --){
            System.out.println(i + "...");
            Thread.sleep(1000);
        }
       
       //closing socket and printing it to user
        s.close();
        System.out.println("Socket Closed");
    }
}
