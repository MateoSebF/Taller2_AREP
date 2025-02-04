package co.edu.eci.arep.webserver.http;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP request with method, URI, headers, parameters, and body.
 * Supports parsing query parameters from the URL.
 * 
 * @author mateo.forero-f
 */
public class HttpRequest {

    private final String method;
    private final URI uri;
    private final Map<String, String> queryParams;
    private final Map<String, String> headers;
    private final String body;

    public static HttpRequest parse(String request) {
        if (request == null || request.isEmpty()) {
            return null;
        }
        System.out.println("This is the request: " + request);
        String[] lines = request.split("\n");
        
        String[] requestLine = lines[0].split(" ");
        String method = requestLine[0];
        URI uri = URI.create(requestLine[1]); 
        Map<String, String> headers = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();
        String body = "";
        boolean readingBody = false;
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].isEmpty()) {
                readingBody = true;
                continue;
            }
            if (readingBody) {
                body += lines[i];
            } else {
                String[] header = lines[i].split(": ");
                headers.put(header[0], header[1]);
            }
        }
        if (uri.getQuery() != null) {
            String[] params = uri.getRawQuery().split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                queryParams.put(keyValue[0], keyValue[1]);
            }
        }
        return new HttpRequest(method, uri, headers, queryParams, body);
    }

    /**
     * Constructs an HttpRequest instance.
     * 
     * @param method      The HTTP method (e.g., GET, POST).
     * @param uri         The requested URI.
     * @param headers     A map containing request headers.
     * @param queryParams A map containing query parameters.
     * @param body        The request body (applicable for POST, PUT, etc.).
     */
    public HttpRequest(String method, URI uri, Map<String, String> headers, Map<String, String> queryParams, String body) {
        this.method = method;
        this.uri = uri;
        this.headers = new HashMap<>(headers);
        this.queryParams = new HashMap<>(queryParams);
        this.body = body;
    }

    /**
     * Returns the HTTP method.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Returns the requested URI.
     */
    public URI getUri() {
        return uri;
    }

    /**
     * Retrieves the value of a specific query parameter.
     * 
     * @param name The parameter key.
     * @return The value of the parameter, or null if not found.
     */
    public String getQueryParam(String name) {
        return queryParams.get(name);
    }

    /**
     * Retrieves the value of a specific header.
     * 
     * @param name The header key.
     * @return The value of the header, or null if not found.
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * Returns the full map of query parameters.
     */
    public Map<String, String> getQueryParams() {
        return new HashMap<>(queryParams);
    }

    /**
     * Returns the full map of headers.
     */
    public Map<String, String> getHeaders() {
        return new HashMap<>(headers);
    }

    /**
     * Returns the request body.
     */
    public String getBody() {
        return body;
    }

}
