package ie.gmit.dip;
/*
 * References for application: (French, 2021), (Oracle, n.d.)
 */

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * The ChatServer class creates a server socket, listens for incoming client requests and handles each request in its own separate thread
 */
public class ChatServer {

	public final static int PORT = 4444; //Set port number
	private Set<String> clientNames = new HashSet<>(); //Use a set to store the collection of client names
	private Set<ClientThread> clientThreads = new HashSet<>(); ////Use a set to store connected client threads

	public static void main(String[] args) { //Main method specifies the entry point of the application

		ExecutorService pool = Executors
				.newFixedThreadPool(30); /*
										  * Executor service creates a pool of threads, use 30 threads here to
										  * execute client requests. Fixed amount helps eliminate swamping
										  */
		//Try-catch exception handling for overall server connection
		try (ServerSocket server = new ServerSocket(PORT)) {/*
															 * Create an instance of a server socket. Listen for
															 * incoming connections and create a socket if there is one
															 */
			System.out.println("Listening for connection on port " + PORT);
			while (true) { //While loop as want to keep going in case another client wants to connect, an infinite loop
				try { //Exception handling for individual client connection
					Socket connection = server
							.accept();  /*
										 * server.connect listens for client connections and accepts. It blocks until a
										 * connection is made, when a connection is made it returns a Socket
										 */
					System.out.println("Client connected from " + connection.getInetAddress() + ":"
							+ connection.getPort());    /*
														 * Give IP address of host and port client is connecting from
														 */
					Callable<Void> task = new ChatServerTask(connection); //Implement
					pool.submit(task); //Submit the task to the thread pool
				} catch (IOException ex) {
				}
			}
		} catch (IOException ex) {
			System.err.println("Server could not be started"); //If couldn't start server IO exception thrown
		}
		  
	}
	
	void broadcastAll(String message, ClientThread) { //Broadcast the message from one client to all
		for (ClientThread user1: clientThreads) {
			user1.sendMessage(message);
		}
	}
	
	void addClientName(String clientName) { //When a client connects the client name is stored
    	clientNames.add(clientName);
    }

	   private static class ChatServerTask implements Callable<Void> {/* Implement the interface Callable which can be 
		   															   * submitted to a thread pool
		   															   */
		   	private Socket connection;

	        ChatServerTask(Socket connection) { //Constructor accepts Socket connection
	            this.connection = connection;
	        }
	        

	        @Override
	        public Void call() { //call() method part of the callable interface
	            try {
	                Writer out = new OutputStreamWriter(connection.getOutputStream());/* Get an output stream from the socket, 
	                																   * create a new output stream writer,
	                																   * write to the output stream and flush.
	                																   */
	                Object in;
					String input = in.readLine();
	                out.write(input.toString() + "\r\n"); //"\r\n": Carriage return, line feed - works on all operating systems
	                out.flush(); //Send straight away
	            } catch (IOException | InterruptedException ex) {
	                System.err.println(ex);
	            } finally {
	                try {
	                    connection.close(); //Close the connection
	                } catch (IOException e) {
	                
	                }
	            }
	            return null;
	        }
	    }
}
