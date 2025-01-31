/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.eci.arep.webserver.Server;

import java.util.HashMap;

/**
 *
 * @author mateo.forero-f
 */
public class HttpRequest {
    
    private HashMap<String, String> queryParams;
    
    public HttpRequest(HashMap<String, String> queryParams){
        this.queryParams = queryParams;
    }
    public String getValues(String name){
        return "Hola";
    }
}
