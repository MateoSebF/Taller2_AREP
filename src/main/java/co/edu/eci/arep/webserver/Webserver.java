package co.edu.eci.arep.webserver;

import java.io.IOException;
import java.net.URISyntaxException;

import co.edu.eci.arep.webserver.Server.HttpServer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
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
        HttpServer.get("/hello", (req, resp) -> "Hello " + req.getValues("name"));
        HttpServer.get("/pi", (req, resp) -> {
            return String.valueOf(Math.PI); 
        });
        HttpServer.get("/murcia", (req, resp) -> {
            return "murcia"; 
        });
        
        HttpServer.main(args);
    }
   
}
