package co.edu.eci.arep.webserver.http;

/**
 *
 * @author mateo.forero-f
 */
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.io.*;
import java.util.function.BiFunction;

/**
 * HttpServer
 */
public class HttpServer implements Runnable {

    public static HashMap<String, BiFunction<HttpRequest, HttpResponse, HttpResponse>> services = new HashMap<>();
    private static String staticRestFilesPath = "";

    /**
     * Main method
     * 
     * @param args
     * 
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        Thread thread = new Thread(new HttpServer());
        thread.start();
    }

    /**
     * Run method that starts the server in the port 35000 and listens for incoming
     * connections
     */
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
                String completeRequest = "";
                while ((inputLine = reader.readLine()) != null) {
                    completeRequest += inputLine + "\n";
                    if (!reader.ready()) {
                        break;
                    }
                }
                HttpRequest request = HttpRequest.parse(completeRequest);
                HttpResponse response = manageRequestFromEndPoint(request);
                System.out.println(response.getStatusCode());
                out.write(response.getBytes());
                // Close streams and socket
                out.close();
                in.close();
                clientSocket.close();
                System.out.println("Connection has been closed");
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

    /**
     * This method manages the request from the endpoint
     * 
     * @param typeRequest the type of request
     * @param resourceURI the URI of the resource
     * @param out         the output stream
     * @throws IOException
     * 
     * @return the response
     */
    public static HttpResponse manageRequestFromEndPoint(HttpRequest httpRequest)
            throws IOException {
        if (httpRequest == null) {
            return sendNotFoundResponse();
        }
        URI resourceURI = httpRequest.getUri();
        if (resourceURI.getPath().endsWith(".html")) {
            return sendGetHtmlString(httpRequest);
        } else if (resourceURI.getPath().endsWith(".css")) {
            return sendGetCssString(httpRequest);
        } else if (resourceURI.getPath().endsWith(".js")) {
            return sendGetJsString(httpRequest);
        } else if (resourceURI.getPath().endsWith(".png") || resourceURI.getPath().endsWith(".jpg")
                || resourceURI.getPath().endsWith(".jpeg") || resourceURI.getPath().endsWith(".gif")
                || resourceURI.getPath().endsWith(".svg")
                || resourceURI.getPath().endsWith(".ico")) {
            return sendGetImageString(httpRequest);
        } else if (resourceURI.getPath().startsWith("/app")) {
            return manageRestAPI(httpRequest);
        } else {
            return sendNotFoundResponse();
        }

    }

    /**
     * This method send a response to the client of a GET request with a HTML file
     * 
     * @param resourceURI the URI of the resource
     * @param out         the output stream
     * 
     * @throws IOException
     * 
     * @return the response
     */
    private static HttpResponse sendGetHtmlString(HttpRequest httpRequest) throws IOException {
        URI resourceURI = httpRequest.getUri();
        String fileName = staticRestFilesPath + resourceURI.getPath();
        System.out.println(fileName);
        if (!new File(fileName).exists()) {
            return sendNotFoundResponse();
        }
        
        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);
        response.addHeader("Content-Type", "text/html");
        String outputLine = "";
        // Leer el contenido desde el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputLine += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setBody(outputLine);
        return response;
    }

    /**
     * This method send a response to the client of a GET request with a CSS file
     * 
     * @param resourceURI the URI of the resource
     * @param out         the output stream
     * 
     * @throws IOException
     * 
     * @return the response
     */
    private static HttpResponse sendGetCssString(HttpRequest httpRequest) throws IOException {
        URI resourceURI = httpRequest.getUri();
        String fileName = staticRestFilesPath + resourceURI.getPath();

        if (!new File(fileName).exists()) {
            return sendNotFoundResponse();
        }
        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);
        response.addHeader("Content-Type", "text/css");
        String outputLine = "";
        // Leer el contenido desde el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputLine += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setBody(outputLine);
        return response;
    }

    /**
     * This method send a response to the client of a GET request with a JS file
     * 
     * @param resourceURI the URI of the resource
     * @param out         the output stream
     * 
     * @throws IOException
     * 
     * @return the response
     */
    private static HttpResponse sendGetJsString(HttpRequest httpRequest) throws IOException {
        URI resourceURI = httpRequest.getUri();
        String fileName = staticRestFilesPath + resourceURI.getPath();

        if (!new File(fileName).exists()) {
            return sendNotFoundResponse();
        }
        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);
        response.addHeader("Content-Type", "text/javascript");
        String outputLine = "";
        // Leer el contenido desde el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputLine += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setBody(outputLine);
        return response;
    }

    /**
     * This method send a response to the client of a GET request with an image
     * 
     * @param resourceURI the URI of the resource
     * @param out         the output stream
     * 
     * @throws IOException
     * 
     * @return the response
     */
    private static HttpResponse sendGetImageString(HttpRequest httpRequest) throws IOException {
        URI resourceURI = httpRequest.getUri();
        String fileName = staticRestFilesPath + resourceURI.getPath();
        System.out.println(fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            return sendNotFoundResponse();
        }
        HttpResponse response = new HttpResponse();
        // Determine the file extension to set the correct Content-Type
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        String contentType = "image/" + extension;
        response.setStatusCode(200);
        response.addHeader("Content-Type", contentType);
        response.addHeader("Content-Length", String.valueOf(file.length()));

        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] imageBytes = inputStream.readAllBytes();
            response.setBody(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * This method send a response to the client of a GET request with an object
     * from the query
     * 
     * @param queryParams the query parameters
     * @param out         the output stream
     * 
     * @throws IOException
     * 
     * @return the response
     */
    private static HttpResponse manageRestAPI(HttpRequest httpRequest)
            throws IOException {
        String endPoint = httpRequest.getMethod().toLowerCase() + "_" + httpRequest.getUri().getPath();
        System.out.println(endPoint);
        if (!services.containsKey(endPoint)) {
            return sendNotFoundResponse();
        }
        HttpResponse response = new HttpResponse();
        response = services.get(endPoint).apply(httpRequest, response);
        return response;
    }

    /**
     * This method send a response to the client of a GET request with a 404 Not
     * Found
     * 
     * 
     * @throws IOException
     */
    public static HttpResponse sendNotFoundResponse() throws IOException {
        HttpResponse response = new HttpResponse();
        response.setStatusCode(404);
        response.addHeader("Content-Type", "text/plain");
        response.setBody("404 Not Found");
        return response;
    }

    public static void staticfiles(String path) {
        String folderPath = ("src/main" + path);
        staticRestFilesPath = folderPath;
        Path pathF = Paths.get(folderPath);
        try {
            Files.createDirectories(pathF);
            System.out.println("Directory is created!");
        } catch (IOException e) {
            System.out.println("Failed to create directory!");
        }
    }

    public static String getStaticRestFilesPath() {
        return staticRestFilesPath;
    }

    public static void get(String route, BiFunction<HttpRequest, HttpResponse, HttpResponse> function) {
        services.put("get_/app" + route, function);
    }

    public static void post(String route, BiFunction<HttpRequest, HttpResponse, HttpResponse> function) {
        services.put("post_/app" + route, function);
    }

    public static void put(String route, BiFunction<HttpRequest, HttpResponse, HttpResponse> function) {
        services.put("put_/app" + route, function);
    }

    public static void delete(String route, BiFunction<HttpRequest, HttpResponse, HttpResponse> function) {
        services.put("delete_/app" + route, function);
    }
}
