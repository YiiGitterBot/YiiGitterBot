package org.YiiCommunity.GitterBot.api;

import org.YiiCommunity.GitterBot.models.postgres.User;

/**
 * Created by Alex on 10/20/15.
 */
public interface Achievement {
    public void onUserChange(User user);
}
