package org.YiiCommunity.GitterBot.utils;

import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.utils.L;

import java.io.*;
import java.net.*;

public class HttpClient {
    /**
     * HTTP GET REQUEST
     *
     * @param query String
     * @return HttpURLConnection
     * @throws Exception
     */
    public static HttpURLConnection get(String query) throws Exception {
        String url = query;

        URL obj = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + GitterBot.gitterToken);

        return conn;
    }

    /**
     * HTTP POST REQUEST
     *
     * @param query  String
     * @param params String
     * @throws Exception
     */
    public static void post(String query, String params) throws Exception {
        String url = query;

        URL obj = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + GitterBot.gitterToken);
        conn.setRequestProperty("Content-Type", "application/json");

        conn.setDoOutput(true);

        try (DataOutputStream output = new DataOutputStream(conn.getOutputStream())) {
            output.write(params.getBytes("UTF-8"));
            output.flush();
        }

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            L.$("Yii gitter bot send message ... [ERROR]\n" + "Response Code: " + responseCode);
        }

        L.$("Yii gitter bot send message ... [SUCCESS]\n" + "Response Code: " + responseCode);
    }
}
