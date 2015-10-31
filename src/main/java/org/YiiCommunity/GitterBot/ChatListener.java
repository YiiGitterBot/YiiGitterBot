package org.YiiCommunity.GitterBot;

import java.util.ArrayList;
import java.util.Set;

import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.utils.L;
import org.reflections.Reflections;

public class ChatListener {

    private ArrayList<Command> commandListeners = new ArrayList<>();

    public ChatListener() {
        loadCommands();

        try {
            Gitter.sendMessage("Let's rock! GitterBot is here!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startListening() {
        Gitter.getStreamingClient().getRoomMessagesStream(GitterBot.getInstance().getConfiguration().getGitterRoomId()).subscribe(messageResponse -> {
            L.$D("Received new string: " + messageResponse.fromUser.username + ": " + messageResponse.text);
            if (!messageResponse.fromUser.username.equals(GitterBot.getInstance().getConfiguration().getBotUsername()))
                for (Command listener : commandListeners) {
                    L.$D("Calling message listener: " + listener.getClass().getName());
                    listener.onMessage(messageResponse);
                }
        });
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
