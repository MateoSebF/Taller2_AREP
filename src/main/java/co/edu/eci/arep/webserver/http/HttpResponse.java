package co.edu.eci.arep.webserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP response with status code, headers, and body.
 * Provides functionality to format the response into a proper HTTP response string.
 * 
 * @author mateo.forero-f
 */
public class HttpResponse {
    
    private int statusCode;
    private Map<String, String> headers;
    private byte[] body;

    public HttpResponse() {
        this.headers = new HashMap<>();
    }
    
    /**
     * Constructs an HttpResponse instance.
     * 
     * @param statusCode The HTTP status code.
     * @param headers    A map containing response headers.
     * @param body       The response body.
     */
    public HttpResponse(int statusCode, Map<String, String> headers, byte[] body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    /**
     * Sets the HTTP status code.
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Returns the HTTP status code.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Add a header to the response.
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }   

    /**
     * Returns the response headers.
     */
    public Map<String, String> getHeaders() {
        return new HashMap<>(headers);
    }

    /**
     * Sets the response body.
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

    /**
     * Sets the response body as a string.
     */
    public void setBody(String body) {
        this.body = body.getBytes();
    }
    
    /**
     * Returns the response body.
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * Converts the HttpResponse object into a valid HTTP response string.
     */
    public byte[] formatResponse() {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 ").append(statusCode).append(" ").append(getStatusMessage()).append("\r\n");
        
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            response.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        
        response.append("\r\n"); // Blank line separating headers from body
        
        byte[] byteResponse = response.toString().getBytes();
        if (body != null) {
            byteResponse = new byte[byteResponse.length + body.length];
            System.arraycopy(response.toString().getBytes(), 0, byteResponse, 0, response.toString().getBytes().length);
            System.arraycopy(body, 0, byteResponse, response.toString().getBytes().length, body.length);

        }
        
        return byteResponse;
    }

    /**
     * Retrieves a status message corresponding to the HTTP status code.
     */
    private String getStatusMessage() {
        return switch (statusCode) {
            case 200 -> "OK";
            case 201 -> "Created";
            case 204 -> "No Content";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "Unknown Status";
        };
    }

    /**
     * Make the response as a string and return the bytes of the response.
     */
    public byte[] getBytes() {
        return formatResponse();
    }
}
