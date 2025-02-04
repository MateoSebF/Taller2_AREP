package co.edu.eci.arep.webserver.echosocket;

/**
 *
 * @author mateo.forero-f
 */
import java.net.*;
import java.io.*;

public class EchoServer {

    /**
     * Method that creates a server socket and waits for a client to connect to it.
     */
    public static void main(String[] args) throws IOException {
        // Create a server socket
        ServerSocket serverSocket = null;
        try {
            // Create a server socket on port 35000
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        // Create a client socket
        Socket clientSocket = null;
        try {
            // Wait for a client to connect
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        // Create a print writer and a buffered reader
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // Create a string to store the input and output
        String inputLine, outputLine;
        // Read the input from the client and write the output
        while ((inputLine = in.readLine()) != null) {
            System.out.println("The client said: '" + inputLine + "'");
            outputLine = "You said: " + inputLine;
            out.println(outputLine);
            if (inputLine.equals("Bye.")) {
                break;
            }
        }
        // Close the reader, writer and the sockets
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("The server and the client have been closed.");
    }
}
