package org.YiiCommunity.GitterBot;

import lombok.Getter;
import org.YiiCommunity.GitterBot.utils.L;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;

public class GitterBot {

    /**
     * Settings Gitter REST & Streaming API
     */
    public static String gitterToken; //https://developer.gitter.im/apps
    public static String gitterRoomId;

    public static String gitterRestUrl;
    public static String gitterStreamingUrl;

    public static boolean debug = false;

    @Getter
    private static FileConfiguration config;

    /**
     * @param args the command line arguments
     * @throws Exception
     */
    public GitterBot(String[] args) throws Exception {
        L.$("Yii Gitter Bot ... [START]");

        loadConfiguration();

        if (args.length > 0) {
            debug = true;
        }

        ChatListener gitter = new ChatListener();

        gitter.streaming();
    }

    private void loadConfiguration() {
        config = YamlConfiguration.loadConfiguration(new File("config.yml"));
        gitterToken = config.getString("gitter.token");
        gitterRoomId = config.getString("gitter.roomId");
        gitterRestUrl = config.getString("gitter.restUrl");
        gitterStreamingUrl = config.getString("gitter.streamingUrl");

        L.$("Configuration loaded");
    }

}
