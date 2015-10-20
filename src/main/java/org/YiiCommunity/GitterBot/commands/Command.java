package org.YiiCommunity.GitterBot.commands;

import org.YiiCommunity.GitterBot.models.json.Message;

/**
 * Created by Alex on 10/20/15.
 */
public interface Command {
    public void onMessage(Message message);
}
