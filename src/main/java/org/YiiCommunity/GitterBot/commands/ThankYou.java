package org.YiiCommunity.GitterBot.commands;

import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.json.Mention;
import org.YiiCommunity.GitterBot.models.json.Message;
import org.YiiCommunity.GitterBot.models.postgres.User;
import org.YiiCommunity.GitterBot.utils.L;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10/20/15.
 */
public class ThankYou implements Command {
    private List<String> words;

    public ThankYou() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("commands/thankyou.yml"));
        words = config.getStringList("words");
    }

    @Override
    public void onMessage(Message message) {
        if (message.getMentions().isEmpty())
            return;

        try {
            for (String item : words) {
                if (message.getText().toLowerCase().startsWith(item) || message.getText().toLowerCase().endsWith(item)) {
                    User giver = User.getUser(message.getFromUser().getUsername());

                    ArrayList<String> sent = new ArrayList<>();
                    for (Mention mention : message.getMentions()) {
                        if (sent.contains(mention.getScreenName()))
                            continue;

                        sent.add(mention.getScreenName());

                        if (mention.getScreenName().equals(message.getFromUser().getUsername())) {
                            Gitter.sendMessage("*@" + message.getFromUser().getUsername() + " самолайк? Как вульгарно!*");
                            continue;
                        }
                        User receiver = User.getUser(mention.getScreenName());
                        receiver.changeCarma(1, giver, message.getText());
                        Gitter.sendMessage("*Спасибо (+1) для @" + receiver.getUsername() + " принято! Текущая карма **" + (receiver.getCarma() >= 0 ? "+" : "-") + receiver.getCarma() + "**.*");
                        receiver.updateAchievements();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            L.$(e.getMessage());
        }
    }
}
