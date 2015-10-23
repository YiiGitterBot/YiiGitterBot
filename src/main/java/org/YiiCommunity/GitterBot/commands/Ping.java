package org.YiiCommunity.GitterBot.commands;

import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.json.Message;

/**
 * Created by Alex on 10/20/15.
 */
public class Ping implements Command {
    @Override
    public void onMessage(Message message) {
        if (message.getText().equals("ping")) {
            try {
                Gitter.sendMessage("pong");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
