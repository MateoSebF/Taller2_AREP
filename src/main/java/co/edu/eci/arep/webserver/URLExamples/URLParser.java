
package co.edu.eci.arep.webserver.urlexamples;

import java.net.MalformedURLException;
import java.net.URL;

   
/**
 *
 * @author mateo.forero-f
 */
public class URLParser {

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws MalformedURLException
    {
        @SuppressWarnings("deprecation")
        URL site = new URL("https://www.escuelaing.edu.co:443/es/index.html?v=465#event");
        System.out.println("Protocol: " + site.getProtocol());
        System.out.println("Authority: " + site.getAuthority());
        System.out.println("Host: " + site.getHost());
        System.out.println("Port: " + site.getPort());
        System.out.println("Path: " + site.getPath());
        System.out.println("Query: " + site.getQuery());
        System.out.println("File: " + site.getFile());
        System.out.println("Ref: " + site.getRef());
    }
    
}
