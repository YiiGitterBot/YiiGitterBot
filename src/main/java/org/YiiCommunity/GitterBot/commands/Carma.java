package org.YiiCommunity.GitterBot.commands;

import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.json.Message;
import org.YiiCommunity.GitterBot.models.postgres.User;
import org.YiiCommunity.GitterBot.utils.L;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alex on 10/23/15.
 */
public class Carma implements Command {
    private List<String> commands = new ArrayList<>();

    public Carma() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("commands/carma.yml"));
        commands = config.getStringList("commands");
    }

    @Override
    public void onMessage(Message message) {
        try {
            for (String item : commands) {
                if (message.getText().equalsIgnoreCase(item)) {
                    User user = User.getUser(message.getFromUser().getUsername());
                    Gitter.sendMessage("@" + user.getUsername() + " твоя карма нынче **" + (user.getCarma() >= 0 ? "+" : "-") + user.getCarma() + "**");
                    return;
                }
            }
            Pattern p = Pattern.compile("(?:" + String.join("|", commands) + ")\\s+@([0-9a-zA-Z-_]+)\\b");
            Matcher m = p.matcher(message.getText().trim());

            while (m.find()) {
                User receiver = User.getUser(m.group(1));
                Gitter.sendMessage("Карма @" + receiver.getUsername() + " нынче **" + (receiver.getCarma() >= 0 ? "+" : "-") + receiver.getCarma() + "**");
                receiver.updateAchievements();
            }
        } catch (Exception e) {
            L.$(e.getMessage());
        }
    }
}
