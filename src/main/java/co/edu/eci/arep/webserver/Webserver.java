package co.edu.eci.arep.webserver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.edu.eci.arep.webserver.http.HttpRequest;

import co.edu.eci.arep.webserver.http.HttpServer;

/**
 *
 * @author mateo.forero-f
 */
public class WebServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        HashMap<String, String> restApiObjects = new HashMap<>();
        HttpServer.staticfiles("/resources/static");

        HttpServer.get("/hello", (req, resp) -> {
            resp.setStatusCode(200);
            resp.addHeader("Content-Type", "text/plain");
            String body = "Hello " + req.getQueryParam("name");
            resp.setBody(body);
            return resp;
        });

        HttpServer.get("/pi", (req, resp) -> {
            resp.setStatusCode(200);
            resp.addHeader("Content-Type", "text/plain");
            resp.setBody(String.valueOf(Math.PI));
            return resp;
        });
        
        HttpServer.get("/mateo", (req, resp) -> {
            resp.setStatusCode(200);
            resp.addHeader("Content-Type", "text/plain");
            resp.setBody("Mateo Forero");
            return resp;
        });

        HttpServer.get("/image", (req, resp) -> {
            try {
                HttpRequest request = HttpRequest.parse("GET /app/frameworkPlaceholder.png HTTP/1.1");
                resp = HttpServer.manageRequestFromEndPoint(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resp;
        });

        HttpServer.get("/objectFromQuery", (req, resp) -> {
            Map<String, String> queryParams = req.getQueryParams();
            String name = queryParams.get("name");
            if (name == null || !restApiObjects.containsKey(name)) {
                try {
                    return HttpServer.sendNotFoundResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resp.setStatusCode(200);
            resp.addHeader("Content-Type", "application/json");
            resp.setBody(restApiObjects.get(name));
            return resp;
        });

        HttpServer.post("/objectFromQuery", (req, resp) -> {
            String name = "";
            name = req.getQueryParams().get("name");
            if (name == null || restApiObjects.containsKey(name)) {
                try {
                    return HttpServer.sendNotFoundResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resp.setStatusCode(200);
            resp.addHeader("Content-Type", "application/json");
            String object = "{\n";
            Map<String, String> queryParams = req.getQueryParams();
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
            resp.setBody(object);
            return resp;
        });

        HttpServer.put("/objectFromQuery", (req, resp) -> {
            String name = req.getQueryParams().get("name");
            if (name == null || !restApiObjects.containsKey(name)) {
                try {
                    return HttpServer.sendNotFoundResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resp.setStatusCode(200);
            resp.addHeader("Content-Type", "application/json");
            String object = "{\n";
            Map<String, String> queryParams = req.getQueryParams();
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
            resp.setBody(object);
            return resp;
        });

        HttpServer.delete("/objectFromQuery", (req, resp) -> {
            System.out.println("DELETE");
            String name = req.getQueryParams().get("name");
            System.out.println("Name: " + name);
            if (name == null || !restApiObjects.containsKey(name)) {
                try {
                    return HttpServer.sendNotFoundResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resp.setStatusCode(200);
            resp.addHeader("Content-Type", "application/json");
            resp.setBody(restApiObjects.get(name));
            restApiObjects.remove(name);
            return resp;
        });

        HttpServer.main(args);
    }

}
