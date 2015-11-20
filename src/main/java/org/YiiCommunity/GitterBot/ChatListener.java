package org.YiiCommunity.GitterBot;

import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.utils.L;

public class ChatListener {

    public ChatListener() {
        Gitter.broadcastMessage("Let's rock! GitterBot is here!");
    }

    public void startListening() {
        for (RoomResponse room : Gitter.getRooms()) {
            Gitter.getStreamingClient().getRoomMessagesStream(room.id).subscribe(messageResponse -> {
                L.$D("Received new string: " + messageResponse.fromUser.username + ": " + messageResponse.text);
                if (!messageResponse.fromUser.username.equals(GitterBot.getInstance().getConfiguration().getBotUsername()))
                    for (Command listener : GitterBot.getInstance().getCommandListeners()) {
                        L.$D("Calling message listener: " + listener.getClass().getName());
                        listener.onMessage(room, messageResponse);
                    }
            });
        }
    }
}
