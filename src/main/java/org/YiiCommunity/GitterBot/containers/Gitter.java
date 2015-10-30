package org.YiiCommunity.GitterBot.containers;

import com.google.gson.Gson;
import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.utils.L;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Gitter {
    public static void sendMessage(String text) throws Exception {

        String params = "{\"text\":" + (new Gson()).toJson(text) + "}";

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
            return;
        }

        L.$D("Yii gitter bot send message ... [SUCCESS] " + "Response Code: " + responseCode);
    }
}
