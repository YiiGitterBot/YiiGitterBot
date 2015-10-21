package org.YiiCommunity.GitterBot;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Set;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.YiiCommunity.GitterBot.commands.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.json.Message;
import org.YiiCommunity.GitterBot.utils.HttpClient;
import org.YiiCommunity.GitterBot.utils.L;
import org.reflections.Reflections;

public class ChatListener {

    private ArrayList<Command> commandListeners = new ArrayList<>();

    public ChatListener() {
        loadCommands();
    }

    /**
     * To connect to the Streaming API, form a HTTP request and consume the resulting stream for as long as is practical.
     * The Gitter servers will hold the connection open indefinitely (barring a server-side error, excessive client-side lag,
     * network hiccups or routine server maintenance). If the server returns an unexpected response code,
     * clients should a wait few seconds before trying again.
     *
     * @throws Exception
     */
    public void streaming() throws Exception {
        Gitter.sendMessage("Let's rock! GitterBot is here!");

        HttpURLConnection conn = HttpClient.get(GitterBot.getInstance().getConfiguration().getGitterStreamingUrl() + GitterBot.getInstance().getConfiguration().getGitterRoomId() + "/chatMessages");

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String data;

            while ((data = input.readLine()) != null) {
                if (!"".equals(data.trim())) {
                    L.$D("Recieved new string: " + data);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonFactory f = new JsonFactory();
                    Message message = mapper.readValue(data, Message.class);
                    if (!message.getFromUser().getUsername().equals(GitterBot.getInstance().getConfiguration().getBotUsername()))
                        for (Command listener : commandListeners) {
                            L.$D("Calling message listener: " + listener.getClass().getName());
                            listener.onMessage(message);
                        }
                }
            }
        } else {
            L.$(L.ANSI_RED + "Streaming ERROR. Response Code: " + responseCode);
        }
    }

    private void loadCommands() {
        Reflections reflections = new Reflections("org.YiiCommunity.GitterBot.commands");

        Set<Class<? extends Command>> annotated = reflections.getSubTypesOf(Command.class);
        for (Class item : annotated) {
            try {
                commandListeners.add((Command) item.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
