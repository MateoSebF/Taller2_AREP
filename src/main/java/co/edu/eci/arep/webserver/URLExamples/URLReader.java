package co.edu.eci.arep.webserver.urlexamples;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * URLReader.java
 */
public class URLReader {

    /**
     * Make a request to a URL and print the response headers and the response body
     */
    public static void main(String[] args) throws Exception {
        String site = "https://escuelaing.s3.amazonaws.com/production/images/primera.min-1440x700_PRHnonm.png?AWSAccessKeyId=AKIAWFY3NGTFJHVI634A&Signature=xs6wJx8R68farSdrvwb5ijBR2l8%3D&Expires=1740153017";
        // Crea el objeto que representa una URL
        @SuppressWarnings("deprecation")
        URL siteURL = new URL(site);
        // Crea el objeto que URLConnection
        URLConnection urlConnection = siteURL.openConnection();
        // Obtiene los campos del encabezado y los almacena en un estructura Map
        Map<String, List<String>> headers = urlConnection.getHeaderFields();
        // Obtiene una vista del mapa como conjunto de pares <K,V>
        // para poder navegarlo
        Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
        // Recorre la lista de campos e imprime los valores
        for (Map.Entry<String, List<String>> entry : entrySet) {
            String headerName = entry.getKey();
            //Si el nombre es nulo, significa que es la linea de estado
            if(headerName !=null){System.out.print(headerName + ":");}
            List<String> headerValues = entry.getValue();
            for (String value : headerValues) {
                System.out.print(value);
            }
            System.out.println("");
            //System.out.println("");
        }

        System.out.println("-------message-body------");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
            System.out.println(inputLine);
        }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}