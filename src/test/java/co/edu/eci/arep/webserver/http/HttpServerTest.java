package co.edu.eci.arep.webserver.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.BeforeAll;

public class HttpServerTest {
    @BeforeAll
    public static void setUp() {
        System.out.println("Iniciando servidor");
        Thread thread = new Thread(new HttpServer());
        HttpServer.staticfiles("/resources/static");
        thread.start();
    }

    @Test
    public void testMakeConnectionHTML() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            HttpURLConnection con = httpConnection.makeConnection("GET", "index.html", "");
            assertEquals(200, con.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetContentTypeHTML() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            HttpURLConnection con = httpConnection.makeConnection("GET", "index.html", "");
            assertEquals("text/html", con.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetResponseHTML() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            String response = httpConnection.getResponse("GET", "index.html", "");
            String expected = "";
            String fileName = "src/main/resources/static/" + "index.html";
            // Leer el contenido desde el archivo
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    expected += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Remove all the spaces and new lines
            expected = expected.replaceAll("\\s", "");
            response = response.replaceAll("\\s", "");

            assertEquals(expected, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMakeConnectionCSS() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            HttpURLConnection con = httpConnection.makeConnection("GET", "styles/home.css", "");
            assertEquals(200, con.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetContentTypeCSS() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            HttpURLConnection con = httpConnection.makeConnection("GET", "styles/home.css", "");
            assertEquals("text/css", con.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetResponseCSS() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            String response = httpConnection.getResponse("GET", "styles/home.css", "");
            String expected = "";
            String fileName = "src/main/resources/static/styles/" + "home.css";
            // Leer el contenido desde el archivo
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    expected += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Remove all the spaces and new lines
            expected = expected.replaceAll("\\s", "");
            response = response.replaceAll("\\s", "");

            assertEquals(expected, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMakeConnectionJS() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            HttpURLConnection con = httpConnection.makeConnection("GET", "scripts/script.js", "");
            assertEquals(200, con.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetContentTypeJS() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            HttpURLConnection con = httpConnection.makeConnection("GET", "scripts/script.js", "");
            assertEquals("text/javascript", con.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetResponseJS() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            String response = httpConnection.getResponse("GET", "scripts/script.js", "");
            String expected = "";
            String fileName = "src/main/resources/static/scripts/" + "script.js";
            // Leer el contenido desde el archivo
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    expected += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Remove all the spaces and new lines
            expected = expected.replaceAll("\\s", "");
            response = response.replaceAll("\\s", "");

            assertEquals(expected, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMakeConnectionImage() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            HttpURLConnection con = httpConnection.makeConnection("GET", "images/placeholder.png", "");
            assertEquals(200, con.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetContentTypeImage() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            HttpURLConnection con = httpConnection.makeConnection("GET", "images/placeholder.png", "");
            assertEquals("image/png", con.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetResponseImage() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            // Request the image from the server
            System.out.println("Requesting image from server...");
            byte[] response = httpConnection.getResponseBytes("GET", "images/placeholder.png", "");
            System.out.println(response.length);
            // Read the expected image file
            String fileName = "src/main/resources/static/images/placeholder.png";
            File file = new File(fileName);
            assertTrue(file.exists(), "The expected image file should exist.");

            byte[] expected = readFileAsBytes(file);
            assertTrue(expected.length > 0, "The expected image file should not be empty.");
            assertTrue(response.length > 0, "The response image should not be empty.");
        } catch (IOException e) {
            fail("Exception occurred during test: " + e.getMessage());
        }
    }

    private byte[] readFileAsBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }

    @Test
    public void testMakeConnectionNotFound() {
        HttpConnectionExample httpConnection = new HttpConnectionExample();
        try {
            HttpURLConnection con = httpConnection.makeConnection("GET", "/notfound", "");
            assertEquals(404, con.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }   
    

}
