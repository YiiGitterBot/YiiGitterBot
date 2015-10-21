package org.YiiCommunity.GitterBot;

import lombok.Getter;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;

/**
 * Created by Alex on 10/20/15.
 */
@Getter
public class Configuration {
    private FileConfiguration config;

    private String gitterToken; //https://developer.gitter.im/apps
    private String gitterRoomId;

    private String gitterRestUrl;
    private String gitterStreamingUrl;

    private String botUsername;

    public Configuration() {
        config = YamlConfiguration.loadConfiguration(new File("config.yml"));
        gitterToken = config.getString("gitter.token");
        gitterRoomId = config.getString("gitter.roomId");
        gitterRestUrl = config.getString("gitter.restUrl");
        gitterStreamingUrl = config.getString("gitter.streamingUrl");

        botUsername = config.getString("username");
    }
}
