package yiigitterbot;

import java.io.*;
import java.net.*;

public class HttpClient {
    /**
     * HTTP GET REQUEST
     * @param query
     * @return
     * @throws Exception 
     */
    public HttpURLConnection get(String query) throws Exception {
        String url = query;
        
        URL obj = new URL(url);
        
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); 
        
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + YiiGitterBot.gitterToken);
        
        return conn;     
    }
    
    /**
     * HTTP POST REQUEST
     * @param query
     * @param params
     * @throws Exception 
     */
    public void post(String query, String params) throws Exception {
        String url = query;
        
        URL obj = new URL(url);
        
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        
        conn.setRequestMethod("POST");        
        conn.setRequestProperty("Authorization", "Bearer " + YiiGitterBot.gitterToken);
        conn.setRequestProperty("Content-Type", "application/json"); 
        
        conn.setDoOutput(true);
        
        try (DataOutputStream output = new DataOutputStream(conn.getOutputStream())) {
            output.write(params.getBytes("UTF-8"));
            output.flush();
        }
        
        int responseCode = conn.getResponseCode();
        
        if (responseCode != 200) {
            System.out.println("Yii gitter bot send message ... [ERROR]\n"
                    + "Response Code: " + responseCode);
        }
        
        System.out.println("Yii gitter bot send message ... [SUCCESS]\n"
                    + "Response Code: " + responseCode);        
    }
}
