package org.YiiCommunity.GitterBot.containers;

import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.utils.L;
import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 10/20/15.
 */
public class Gitter {
    public static void sendMessage(String text) throws Exception {
        JSONObject requestJson = new JSONObject();

        requestJson.put("text", text);

        String params = requestJson.toString();

        String url = GitterBot.getInstance().getConfiguration().getGitterRestUrl() + "rooms/" + GitterBot.getInstance().getConfiguration().getGitterRoomId() + "/chatMessages";

        URL obj = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + GitterBot.getInstance().getConfiguration().getGitterToken());
        conn.setRequestProperty("Content-Type", "application/json");

        conn.setDoOutput(true);

        try (DataOutputStream output = new DataOutputStream(conn.getOutputStream())) {
            output.write(params.getBytes("UTF-8"));
            output.flush();
        }

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            L.$D("Yii gitter bot send message ... [ERROR] " + "Response Code: " + responseCode);
        }

        L.$D("Yii gitter bot send message ... [SUCCESS] " + "Response Code: " + responseCode);
    }
}
