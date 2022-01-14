package ie.gmit.dip;

import java.net.*;
import java.io.*;

/*
 * The ChatClient class creates a socket connection to the server
 */
public class ChatClient {

    public static final int PORT = 4444; 
    private ChatClient client;

    public static void main(String[] args) {
        String hostname = args.length > 0 ? args[0] : "localhost";/* Check if any command line arguments passed in, 
		 														   * if there are take the one at position 0 of the array
		 														   * and assign it to localhost. If not, use default
		 														   */
        // Try with resources implements closeable which helps to avoid memory leak
        try (Socket socket = new Socket(hostname, PORT)) {// Create socket and pass in port at same time
            System.out.println("Connected to Server on host " + hostname);
            socket.setSoTimeout(15000);/* Set a timeout on blocking socket operations, the program will wait until these
            							  are completed and can slow down application, therefore set a timeout (15000
            							  milliseconds here
            						   */			
            InputStream input = socket.getInputStream();// Read what the remote server is sending  
            byte[] inputBytes = input.readAllBytes();// Read all bytes on input and put into a byte array
            String in = new String(inputBytes);// Convert the byte array into a string
            System.out.println(in);//Print out 
        } catch (UnknownHostException e) {
            System.out.println("Unable to connect to host");// If client can't connect to host, error message will be printed
            e.printStackTrace(); //Prints backtrace to the standard error stream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
    	 
        Console console = System.console(); //Write to console
 
        String userName = console.readLine("\nEnter your name: "); //Ask user to enter name
        ChatClient.setUserName(userName); //Set username
        PrintStream writer = null;
		writer.println(userName);
 
        String text;
 
        do {
            text = console.readLine("[" + userName + "]: ");
            writer.println(text);
 
        } while (!text.equals("q"));//Quit if user presses '\q'
 
        try {
            Socket socket = null;
			socket.close(); //Close socket
        } catch (IOException ex) {

        }
       
    }
	private static void setUserName(String userName) {
	
	}
	
	public void connect (Socket socket, ChatClient client) { //Read input from user and send to server
            this.socket = socket;
            this.client = client;
	}
}
