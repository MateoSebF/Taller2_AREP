package co.edu.eci.arep.webserver.http;

/**
 *
 * @author mateo.forero-f
 */
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.function.BiFunction;

/**
 * HttpServer
 */
public class HttpServer implements Runnable {

    public static HashMap<String, String> restApiObjects = new HashMap<>();
    public static HashMap<String, BiFunction<HttpRequest, HttpResponse, HttpResponse>> services = new HashMap<>();

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
                HttpResponse response = manageRequestFromEndPoint(request, out);
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
    private static HttpResponse manageRequestFromEndPoint(HttpRequest httpRequest, OutputStream out)
            throws IOException {
        if (httpRequest == null) {
            return sendNotFoundResponse(out);
        }
        URI resourceURI = httpRequest.getUri();
        if (resourceURI.getPath().startsWith("/app")) {
            return manageRestAPI(httpRequest, out);
        } else if (resourceURI.getPath().endsWith(".html")) {
            return sendGetHtmlString(httpRequest, out);
        } else if (resourceURI.getPath().endsWith(".css")) {
            return sendGetCssString(httpRequest, out);
        } else if (resourceURI.getPath().endsWith(".js")) {
            return sendGetJsString(httpRequest, out);
        } else if (resourceURI.getPath().endsWith(".png") || resourceURI.getPath().endsWith(".jpg")
                || resourceURI.getPath().endsWith(".jpeg") || resourceURI.getPath().endsWith(".gif")
                || resourceURI.getPath().endsWith(".svg")
                || resourceURI.getPath().endsWith(".ico")) {
            return sendGetImageString(httpRequest, out);
        } else {
            return sendNotFoundResponse(out);
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
    private static HttpResponse sendGetHtmlString(HttpRequest httpRequest, OutputStream out) throws IOException {
        URI resourceURI = httpRequest.getUri();
        String fileName = "src/main/resources/static/pages/" + resourceURI.getPath();
        if (!new File(fileName).exists()) {
            return sendNotFoundResponse(out);
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
    private static HttpResponse sendGetCssString(HttpRequest httpRequest, OutputStream out) throws IOException {
        URI resourceURI = httpRequest.getUri();
        String fileName = "src/main/resources/static/styles/" + resourceURI.getPath();
        if (!new File(fileName).exists()) {
            return sendNotFoundResponse(out);
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
    private static HttpResponse sendGetJsString(HttpRequest httpRequest, OutputStream out) throws IOException {
        URI resourceURI = httpRequest.getUri();
        String fileName = "src/main/resources/static/scripts/" + resourceURI.getPath();
        if (!new File(fileName).exists()) {
            return sendNotFoundResponse(out);
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
    private static HttpResponse sendGetImageString(HttpRequest httpRequest, OutputStream out) throws IOException {
        URI resourceURI = httpRequest.getUri();
        String fileName = "src/main/resources/static/images" + resourceURI.getPath();
        File file = new File(fileName);
        if (!file.exists()) {
            return sendNotFoundResponse(out);
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
    private static HttpResponse manageRestAPI(HttpRequest httpRequest, OutputStream out)
            throws IOException {
        if (httpRequest.getUri().getPath().equals("/app/objectFromQuery")) {
            if (httpRequest.getMethod().equals("POST")) {
                return manageRestPostObjectFromQuery(httpRequest, out);
            } else if (httpRequest.getMethod().equals("GET")) {
                return manageRestGetObjectFromQuery(httpRequest, out);
            }
            return sendNotFoundResponse(out);
        }
        String endPoint = httpRequest.getMethod().toLowerCase() + "_" + httpRequest.getUri().getPath();
        System.out.println(endPoint);
        if (!services.containsKey(endPoint)) {
            return sendNotFoundResponse(out);
        }
        HttpResponse response = new HttpResponse();
        response = services.get(endPoint).apply(httpRequest, response);
        return response;
    }

    /**
     * This method send a response to the client of a GET request with a 404 Not
     * Found
     * 
     * @param out the output stream
     * 
     * @throws IOException
     */
    private static HttpResponse sendNotFoundResponse(OutputStream out) throws IOException {
        HttpResponse response = new HttpResponse();
        response.setStatusCode(404);
        response.addHeader("Content-Type", "text/plain");
        response.setBody("404 Not Found");
        return response;
    }

    private static HttpResponse manageRestGetObjectFromQuery(HttpRequest httpRequest, OutputStream out)
            throws IOException {
        Map<String, String> queryParams = httpRequest.getQueryParams();
        String name = queryParams.get("name");
        if (name == null || !restApiObjects.containsKey(name)) {
            return sendNotFoundResponse(out);
        }
        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);
        response.addHeader("Content-Type", "application/json");
        response.setBody(restApiObjects.get(name));
        return response;
    }

    private static HttpResponse manageRestPostObjectFromQuery(HttpRequest httpRequest, OutputStream out)
            throws IOException {
        String name = "";
        name = httpRequest.getQueryParams().get("name");
        if (name == null || restApiObjects.containsKey(name)) {
            return sendNotFoundResponse(out);
        }
        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);
        response.addHeader("Content-Type", "application/json");
        String object = "{\n";
        Map<String, String> queryParams = httpRequest.getQueryParams();
        ArrayList<String> keys = new ArrayList<>(queryParams.keySet());

        for (String key : keys) {
            String value = queryParams.get(key);
            System.out.println("Key: " + key + " Value: " + value);
            // Decode the value
            value = URLDecoder.decode(value, StandardCharsets.UTF_8);
            if (keys.indexOf(key) != keys.size() - 1) {
                object += "\"" + key + "\" : \"" + value + "\" ,\n";
            } else {
                object += "\"" + key + "\" : \"" + value + "\"\n";
            }
        }
        object += "}";
        restApiObjects.put(name, object);
        response.setBody(object);
        return response;
    }

    public static void staticfiles(String path) {
        return;
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
