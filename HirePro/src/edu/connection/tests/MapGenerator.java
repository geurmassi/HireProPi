/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.tests;

import java.util.HashMap;
import java.util.Map;

public class MapGenerator {
    /**
     * This method generates a map API.
     *
     * @return Map<String, String>: The generated map API
     */
    public static Map<String, String> generateMapAPI() {
        Map<String, String> mapAPI = new HashMap<>();

        // Add sample key-value pairs to the map
        mapAPI.put("key1", "value1");
        mapAPI.put("key2", "value2");
        mapAPI.put("key3", "value3");

        return mapAPI;
    }

    public static void main(String[] args) {
        // Generate the map API
        Map<String, String> api = generateMapAPI();

        // Print the generated map API
        for (Map.Entry<String, String> entry : api.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}