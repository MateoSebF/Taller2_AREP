package co.edu.eci.arep.webserver;

import java.io.IOException;
import java.net.URISyntaxException;

import co.edu.eci.arep.webserver.Server.HttpServer;

/**
 *
 * @author mateo.forero-f
 */
public class WebServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, URISyntaxException  {
        // Run the http server
        HttpServer.main(args);
    }
}
