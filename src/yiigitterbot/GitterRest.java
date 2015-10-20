package yiigitterbot;

import java.io.*;
import java.net.*;
import org.json.simple.*;

public class GitterRest {
    
    HttpClient http = new HttpClient();
    
    /**
     * To connect to the Streaming API, form a HTTP request and consume the resulting stream for as long as is practical. 
     * The Gitter servers will hold the connection open indefinitely (barring a server-side error, excessive client-side lag, 
     * network hiccups or routine server maintenance). If the server returns an unexpected response code, 
     * clients should a wait few seconds before trying again.
     * @throws Exception 
     */
    public void streaming() throws Exception {
        HttpURLConnection conn = http.get(YiiGitterBot.gitterStreamingUrl + YiiGitterBot.gitterRoomId + "/chatMessages");
        
        int responseCode = conn.getResponseCode();
        
        if (responseCode == 200) {
            ThankCommands thank = new ThankCommands();
            
            System.out.println("Response Code: " + responseCode);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String data = null;
            
            while ((data = input.readLine()) != null) {
                if (!"".equals(data.trim())) {
                    thank.run(data);
                    //System.out.println("Response Code: " + data);
                }
            }
        } else {
            System.out.println("Response Code: " + responseCode);
        }
    }
    
    /**
     * Send a Message
     * Send a message to a room.
     * @param text
     * @throws Exception 
     */
    public void sendMessages(String text) throws Exception {
        JSONObject requestJson = new JSONObject();
        
        requestJson.put("text", text);
        
        String params = requestJson.toString();
        
        http.post(YiiGitterBot.gitterRestUrl + "rooms/" + YiiGitterBot.gitterRoomId + "/chatMessages", params);
    }
}
