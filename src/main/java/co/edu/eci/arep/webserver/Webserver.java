package co.edu.eci.arep.webserver;

import java.io.IOException;
import java.net.URISyntaxException;

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
    public static void main(String[] args) throws IOException, URISyntaxException  {
        HttpServer.staticfiles("/webroot");
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
        
        HttpServer.main(args);
    }
   
}
