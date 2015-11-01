package org.YiiCommunity.GitterBot;

import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.utils.L;

public class ChatListener {

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
                for (Command listener : GitterBot.getInstance().getCommandListeners()) {
                    L.$D("Calling message listener: " + listener.getClass().getName());
                    listener.onMessage(messageResponse);
                }
        });
    }

    private void loadCommands() {

    }
}
