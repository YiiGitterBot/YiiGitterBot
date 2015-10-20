package org.YiiCommunity.GitterBot.commands;

import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.json.Message;
import org.YiiCommunity.GitterBot.models.json.MessageSender;
import org.YiiCommunity.GitterBot.utils.L;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alex on 10/20/15.
 */
public class ThankYou implements Command {
    private String thankPatternWords = "(?:Thank|Thanks|Спасибо|thank|thanks|спасибо)";
    private String thankPatternUsernameWords = "@([0-9a-zA-Z-_]+)\\s+" + thankPatternWords + "\\b";

    @Override
    public void onMessage(Message message) {
        Pattern p = Pattern.compile(thankPatternUsernameWords);
        Matcher m = p.matcher(message.getText());

        while (m.find()) {
            try {
                if (!message.getFromUser().getUsername().equals(m.group(1))) {
                    Gitter.sendMessages("*Спасибо (+1) для @" + m.group(1) + " принято! Текущая карма **+0**.*");
                } else {
                    Gitter.sendMessages("*@" + m.group(1) + " СЛАВА ПУТИНУ!!!*");
                }
            } catch (Exception e) {
                L.$(e.getMessage());
            }
        }
    }
}
