package org.YiiCommunity.GitterBot.api;

import org.YiiCommunity.GitterBot.utils.JarUtils;
import org.YiiCommunity.GitterBot.utils.L;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public abstract class Configurable {

    private FileConfiguration config;

    public FileConfiguration getConfig() {
        if (config == null)
            loadConfig();
        return config;
    }

    private void loadConfig() {
        try {
            File jar = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            File configFile = new File(FilenameUtils.removeExtension(jar.getAbsolutePath()) + "/plugin.yml");
            L.$D("Loading configuration for " + FilenameUtils.removeExtension(jar.getName()) + " plugin (" + configFile.toURI().toURL() + ")");
            if (!configFile.exists()) {
                L.$("Configuration file for " + FilenameUtils.removeExtension(jar.getName()) + " plugin not exists. Trying to get from jar.");
                JarUtils.unpackResource(jar, "plugin.yml", new File(FilenameUtils.removeExtension(jar.getAbsolutePath())));
            }
            config = YamlConfiguration.loadConfiguration(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
