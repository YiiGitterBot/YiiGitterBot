package org.YiiCommunity.GitterBot.commands;

import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.json.Message;
import org.YiiCommunity.GitterBot.models.postgres.User;
import org.YiiCommunity.GitterBot.utils.L;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alex on 10/20/15.
 */
public class ThankYou implements Command {
    private String thankPatternWords;

    public ThankYou() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("commands/thankyou.yml"));
        List<String> words = config.getStringList("words");
        thankPatternWords = "(?:" + String.join("|", words) + ")";
    }

    @Override
    public void onMessage(Message message) {
        Pattern p = Pattern.compile("@([0-9a-zA-Z-_]+)\\s+" + thankPatternWords + "\\b");
        Matcher m = p.matcher(message.getText());

        while (m.find()) {
            try {
                if (!message.getFromUser().getUsername().equals(m.group(1))) {
                    User giver = User.getUser(message.getFromUser().getUsername());
                    User receiver = User.getUser(m.group(1));
                    receiver.changeCarma(1, giver, message.getText());
                    Gitter.sendMessage("*Спасибо (+1) для @" + m.group(1) + " принято! Текущая карма **" + (receiver.getCarma() >= 0 ? "+" : "-") + receiver.getCarma() + "**.*");
                    receiver.updateAchievements();
                } else {
                    Gitter.sendMessage("*@" + m.group(1) + " СЛАВА ПУТИНУ!!!*");
                }
            } catch (Exception e) {
                L.$(e.getMessage());
            }
        }
    }
}
