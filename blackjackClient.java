import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.*;

/*To do:
    * comment
    * Accept tcp connection from port 1706
    * Print out hello when accepting socket
    * Close socket
    * print bye when closing socket
*/
import java.io.OutputStream;

/**
 * @author Joy Janney
 * Course:
 * Professor:
 * Date Created: 3/25/26
 * Description: 
 */

public class blackjackClient{
    final public static int PORT = 1706;
    final public static String IP = "localhost";
    public static void main(String[] args) throws Exception
    {
        Socket s = new Socket(IP, PORT);
        /*InputStream in = s.getInputStream();
        byte [] buf = new byte[1024];
        int n = in.read(buf);
        System.out.println(new String(buf, 0, n));*/
        System.out.println("Socket Created");



        System.out.println("Closing socket connection in 10 seconds...");
        //https://medium.com/@AlexanderObregon/building-a-countdown-timer-in-java-using-loops-for-beginners-67db5d461c18
        for(int i = 9; i > 0; i --){
            System.out.println(i + "...");
            Thread.sleep(1000);
        }
        //https://www.geeksforgeeks.org/java/socket-programming-in-java/
        DataOutputStream socketOutput = new DataOutputStream(s.getOutputStream());
        socketOutput.writeUTF("Goodbye");
        s.close();
        System.out.println("Socket Closed");
    }
}
