package org.YiiCommunity.GitterBot.api;

import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;

public abstract class Command extends Configurable {
    public abstract void onMessage(RoomResponse room, MessageResponse message);
}
