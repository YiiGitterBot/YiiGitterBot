package org.YiiCommunity.GitterBot.containers;

import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.utils.HttpClient;
import org.json.simple.JSONObject;

/**
 * Created by Alex on 10/20/15.
 */
public class Gitter {
    public static void sendMessage(String text) throws Exception {
        JSONObject requestJson = new JSONObject();

        requestJson.put("text", text);

        String params = requestJson.toString();

        HttpClient.post(GitterBot.getInstance().getConfiguration().getGitterRestUrl() + "rooms/" + GitterBot.getInstance().getConfiguration().getGitterRoomId() + "/chatMessages", params);
    }
}
