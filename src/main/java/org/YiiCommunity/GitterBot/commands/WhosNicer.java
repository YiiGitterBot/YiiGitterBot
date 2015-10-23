package org.YiiCommunity.GitterBot.commands;

import com.avaje.ebean.Ebean;
import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.json.Message;
import org.YiiCommunity.GitterBot.models.postgres.User;

/**
 * Created by Alex on 10/23/15.
 */
public class WhosNicer implements Command {
    @Override
    public void onMessage(Message message) {
        if (message.getText().contains("@" + GitterBot.getInstance().getConfiguration().getBotUsername() + " кто на свете всех милее")) {
            User obj = Ebean.find(User.class).order().desc("carma").setMaxRows(1).findUnique();
            try {
                Gitter.sendMessage("Нынче на престоле @" + obj.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
