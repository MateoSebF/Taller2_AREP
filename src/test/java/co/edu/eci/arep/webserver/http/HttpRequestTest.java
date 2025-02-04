package co.edu.eci.arep.webserver.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    @Test
    public void testParseGetMethod() {
        String requestExpected = "";
        String pathToExampleRequest = "src/main/resources/static/examples/request.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToExampleRequest))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestExpected += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.parse(requestExpected);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void testParseGetUri() {
        String requestExpected = "";
        String pathToExampleRequest = "src/main/resources/static/examples/request.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToExampleRequest))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestExpected += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.parse(requestExpected);
        assertEquals("/index.html", request.getUri().toString());
    }

    @Test
    public void testParseGetHeaders() {
        String requestExpected = "";
        String pathToExampleRequest = "src/main/resources/static/examples/request.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToExampleRequest))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestExpected += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.parse(requestExpected);
        assertEquals("localhost:35000", request.getHeaders().get("Host"));
        assertEquals("keep-alive", request.getHeaders().get("Connection"));
        assertEquals("\"Not A(Brand\";v=\"8\", \"Chromium\";v=\"132\", \"Google Chrome\";v=\"132\"", request.getHeaders().get("sec-ch-ua"));
        assertEquals("?0", request.getHeaders().get("sec-ch-ua-mobile"));
        assertEquals("\"Windows\"", request.getHeaders().get("sec-ch-ua-platform"));
        assertEquals("1", request.getHeaders().get("Upgrade-Insecure-Requests"));
        assertEquals("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36", request.getHeaders().get("User-Agent"));
        assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7", request.getHeaders().get("Accept"));
        assertEquals("none", request.getHeaders().get("Sec-Fetch-Site"));
        assertEquals("navigate", request.getHeaders().get("Sec-Fetch-Mode"));
        assertEquals("?1", request.getHeaders().get("Sec-Fetch-User"));
        assertEquals("document", request.getHeaders().get("Sec-Fetch-Dest"));
        assertEquals("gzip, deflate, br, zstd", request.getHeaders().get("Accept-Encoding"));
        assertEquals("es-US,es-419;q=0.9,es;q=0.8,en;q=0.7,fr;q=0.6", request.getHeaders().get("Accept-Language"));
    }

    @Test
    public void testParseGetQueryParams() {
        String requestExpected = "";
        String pathToExampleRequest = "src/main/resources/static/examples/request.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToExampleRequest))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestExpected += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.parse(requestExpected);
        assertEquals(0, request.getQueryParams().size());
    }

    @Test
    public void testParseGetBody() {
        String requestExpected = "";
        String pathToExampleRequest = "src/main/resources/static/examples/request.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToExampleRequest))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestExpected += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.parse(requestExpected);
        assertEquals("", request.getBody());
    }

}
