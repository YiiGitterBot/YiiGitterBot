package org.YiiCommunity.GitterBot.api;

import com.amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;

/**
 * Created by Alex on 10/20/15.
 */
public interface Command {
    public void onMessage(MessageResponse message);
}
