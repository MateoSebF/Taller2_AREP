package co.edu.eci.arep.webserver.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionExample {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://localhost:35000";

    public static void main(String[] args) throws IOException {
        HttpConnectionExample http = new HttpConnectionExample();
        System.out.println("Testing 1 - Send Http GET request");
        http.makeConnection("GET", "/index.html", "");
    }

    public HttpConnectionExample(){

    }

    public HttpURLConnection makeConnection(String method, String endPoint, String query) throws IOException {
        String newUrl = GET_URL + endPoint + query;
        @SuppressWarnings("deprecation")
        URL obj = new URL(newUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        return con;
    }

    public String getResponse(String method, String endPoint, String query) throws IOException {
        String newUrl = GET_URL + endPoint + query;
        @SuppressWarnings("deprecation")
        URL obj = new URL(newUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            return "GET request not worked";
        }
    }

    public byte[] getResponseBytes(String method, String endPoint, String query) throws IOException {
        String newUrl = GET_URL + endPoint + query;
        @SuppressWarnings("deprecation")
        URL obj = new URL(newUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString().getBytes();
        } else {
            return "GET request not worked".getBytes();
        }
    }
} 