package org.YiiCommunity.GitterBot.api;

import lombok.Getter;
import lombok.Setter;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;

/**
 * Created by alex on 10/30/15.
 */
public abstract class Configurable {

    @Getter
    @Setter
    private String configurationsFolder = ".";

    private FileConfiguration config;

    public FileConfiguration getConfig() {
        if (config == null)
            loadConfig();
        return config;
    }

    private void loadConfig() {
        config = YamlConfiguration.loadConfiguration(new File(this.configurationsFolder + "/" + this.getClass().getSimpleName() + ".yml"));
    }
}
