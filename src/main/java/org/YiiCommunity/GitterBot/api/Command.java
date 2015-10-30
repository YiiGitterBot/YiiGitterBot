package org.YiiCommunity.GitterBot.api;

import com.amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;
import org.YiiCommunity.GitterBot.utils.L;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;

/**
 * Created by alex on 10/30/15.
 */
public abstract class Command extends Configurable {

    public Command() {
        setConfigurationsFolder("commands");
    }

    public abstract void onMessage(MessageResponse message);
}
