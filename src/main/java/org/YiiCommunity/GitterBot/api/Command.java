package org.YiiCommunity.GitterBot.api;

import com.amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.room.RoomResponse;
import org.YiiCommunity.GitterBot.utils.L;
import org.YiiCommunity.GitterBot.utils.yuml.file.FileConfiguration;
import org.YiiCommunity.GitterBot.utils.yuml.file.YamlConfiguration;

import java.io.File;

public abstract class Command extends Configurable {
    public abstract void onMessage(RoomResponse room, MessageResponse message);
}
