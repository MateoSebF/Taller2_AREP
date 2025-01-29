package co.edu.eci.arep.webserver.Server;

/**
 *
 * @author mateo.forero-f
 */
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.*;

public class HttpServer implements Runnable {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Thread thread = new Thread(new HttpServer());
        thread.start();
    }

    public void run() {
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

            try (
                    InputStream in = clientSocket.getInputStream()) {
                OutputStream out = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String inputLine;
                boolean isFirstLine = true;
                String typeRequest = "";
                String file = "";
                while ((inputLine = reader.readLine()) != null) {
                    if (isFirstLine) {
                        typeRequest = inputLine.split(" ")[0];
                        file = inputLine.split(" ")[1];
                        isFirstLine = false;
                    }
                    System.out.println("Received: " + inputLine);
                    if (!reader.ready()) {
                        break;
                    }
                }

                URI resourceURI = new URI(file);
                manageRequestFromEndPoint(typeRequest, resourceURI, out);

                // Close streams and socket
                out.close();
                in.close();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void manageRequestFromEndPoint(String typeRequest, URI resourceURI, OutputStream out) {
        HashMap<String, String> queryParams = new HashMap<>();
        if (resourceURI.getQuery() != null) {
            String[] params = resourceURI.getRawQuery().split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                queryParams.put(keyValue[0], keyValue[1]);
            }
        }
        try {
            if (resourceURI.getPath().endsWith(".html")) {
                sendGetHtmlString(resourceURI, out);
            } else if (resourceURI.getPath().endsWith(".css")) {
                sendGetCssString(resourceURI, out);
            } else if (resourceURI.getPath().endsWith(".js")) {
                sendGetJsString(resourceURI, out);
            } else if (resourceURI.getPath().endsWith(".png") || resourceURI.getPath().endsWith(".jpg")
                    || resourceURI.getPath().endsWith(".jpeg") || resourceURI.getPath().endsWith(".gif")
                    || resourceURI.getPath().endsWith(".svg")
                    || resourceURI.getPath().endsWith(".ico")) {
                sendGetImageString(resourceURI, out);
            } else if (resourceURI.getPath().startsWith("/app/getObjectFromQuery")) {
                getObjectFromQuery(queryParams, out);
            } else {
                sendNotFoundResponse(out);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendGetHtmlString(URI resourceURI, OutputStream out) throws IOException {
        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
        String fileName = "src/main/resources/static/pages/" + resourceURI.getPath();
        // Leer el contenido desde el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputLine += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(outputLine.getBytes());
    }

    private static void sendGetCssString(URI resourceURI, OutputStream out) throws IOException {
        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/css\r\n"
                + "\r\n";
        String fileName = "src/main/resources/static/styles/" + resourceURI.getPath();
        // Leer el contenido desde el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputLine += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(outputLine.getBytes());
    }

    private static void sendGetJsString(URI resourceURI, OutputStream out) throws IOException {
        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/javascript\r\n"
                + "\r\n";
        String fileName = "src/main/resources/static/scripts/" + resourceURI.getPath();
        // Leer el contenido desde el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputLine += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(outputLine.getBytes());
    }

    private static void sendGetImageString(URI resourceURI, OutputStream out) throws IOException {
        String fileName = "src/main/resources/static/images" + resourceURI.getPath();
        File file = new File(fileName);

        if (!file.exists()) {
            sendNotFoundResponse(out);
            return;
        }

        // Determine the file extension to set the correct Content-Type
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        String contentType = "image/" + extension;

        // Write HTTP headers
        String headers = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + file.length() + "\r\n"
                + "\r\n";
        out.write(headers.getBytes());
        out.flush();

        // Write the image content as raw bytes
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        out.flush();
    }

    private static void getObjectFromQuery(HashMap<String, String> queryParams, OutputStream out) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\n";
        ArrayList<String> keys = new ArrayList<>(queryParams.keySet());
        for (String key : keys) {
            String value = queryParams.get(key);
            // Decode the value
            value = URLDecoder.decode(value, StandardCharsets.UTF_8);
            if (keys.indexOf(key) != keys.size() - 1) {
                response += "\"" + key + "\" : \"" + value + "\" ,\n";
            } else {
                response += "\"" + key + "\" : \"" + value + "\"\n";
            }
        }
        response += "}";
        out.write(response.getBytes());
    }

    private static void sendNotFoundResponse(OutputStream out) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: text/plain\r\n"
                + "\r\n"
                + "404 Not Found";
        out.write(response.getBytes());
        out.flush();
    }
}
