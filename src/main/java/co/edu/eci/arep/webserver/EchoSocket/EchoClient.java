package co.edu.eci.arep.webserver.echosocket;

/**
 *
 * @author mateo.forero-f
 */
import java.io.*;
import java.net.*;

public class EchoClient {

    /**
     * Method that creates a client socket and connects to a server socket.
     */
    public static void main(String[] args) {
        // Create a client socket
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            // Create a client socket on port 35000 that is the same as the server socket
            echoSocket = new Socket("127.0.0.1", 35000);
            // Create a print writer and a buffered reader
            // The print writer is used to write the output to the server
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            // The buffered reader is used to read the input from the server
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don’t know about host!.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for "
                    + "the connection to: localhost.");
            System.exit(1);
        }
        // Create a buffered reader to read the input in the console
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        try {
            // Read the input from the console and write the output
            while ((userInput = stdIn.readLine()) != null) {
                // Write the input to the server
                out.println(userInput);
                System.out.println("The server said: '" + in.readLine() + "'");
            }
            // Close the reader, writer and the socket
            // Verify the case when the server is closed
            in.close();
            stdIn.close();
            echoSocket.close();
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for "
                    + "the connection to: localhost.");
            System.exit(1);
        }
        out.close();

    }
}
