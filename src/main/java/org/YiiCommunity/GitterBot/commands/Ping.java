package org.YiiCommunity.GitterBot.commands;

import com.amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;
import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;

/**
 * Created by Alex on 10/20/15.
 */
public class Ping implements Command {
    @Override
    public void onMessage(MessageResponse message) {
        if (message.text.equals("ping")) {
            try {
                Gitter.sendMessage("pong");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
