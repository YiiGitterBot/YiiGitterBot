package org.YiiCommunity.GitterBot;

import lombok.Getter;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.utils.JarUtils;
import org.YiiCommunity.GitterBot.utils.L;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;
import java.net.URISyntaxException;

@Getter
public class Configuration {
    private FileConfiguration config;

    private String gitterToken;

    private String gitHubToken;

    private String botUsername;

    public Configuration() {
        if (!new File("config.yml").exists()) {
            try {
                JarUtils.unpackResource(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()), "config.yml", new File("./"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(new File("config.yml"));
        gitterToken = config.getString("gitter.token");

        gitHubToken = config.getString("github.token");
    }

    public String getBotUsername() {
        if (botUsername == null) {
            botUsername = Gitter.getApiClient().getCurrentUser().toBlocking().first().username;
        }
        return botUsername;
    }
}
