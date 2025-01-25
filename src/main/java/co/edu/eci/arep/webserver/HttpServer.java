package co.edu.eci.arep.webserver;

/**
 *
 * @author mateo.forero-f
 */
import java.net.*;
import java.io.*;

public class HttpServer {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean isFirstLine = true;
            String file = "";
            while ((inputLine = in.readLine()) != null) {
                if (isFirstLine) {
                    file = inputLine.split(" ")[1];
                    isFirstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            URI resourceURI = new URI(file);
                System.out.println("URI: " + resourceURI);
            if (resourceURI.getPath().startsWith("/app/hello")){
                String query = resourceURI.getQuery();
                outputLine = helloRestService(resourceURI.getPath(),query.split("=")[1]);
            } 
            else {

                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n";
                String fileName = "C:\\Users\\mateo.forero-f\\Documents\\NetBeansProjects\\webserver\\src\\main\\java\\co\\edu\\eci\\arep\\webserver\\output.html";
                System.out.println();
                // Leer el contenido desde el archivo
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputLine += line + "\n";
                    }
                    System.out.println("Contenido cargado con éxito.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
    
    private static String helloRestService(String path, String query){
        System.out.println(query);
        String response = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + "'{\"name\":\""
                        + query
                        + "\", \"age\":30, \"car\":null}'";
        return response;
    }
}
