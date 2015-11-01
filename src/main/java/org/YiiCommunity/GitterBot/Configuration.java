package org.YiiCommunity.GitterBot;

import lombok.Getter;
import org.YiiCommunity.GitterBot.utils.JarUtils;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;
import java.net.URISyntaxException;

@Getter
public class Configuration {
    private FileConfiguration config;

    private String gitterToken;
    private String gitterRoomId;

    private String gitterRestUrl;
    private String gitterStreamingUrl;

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
        gitterRoomId = config.getString("gitter.roomId");
        gitterRestUrl = config.getString("gitter.restUrl");
        gitterStreamingUrl = config.getString("gitter.streamingUrl");

        botUsername = config.getString("username");
    }
}
