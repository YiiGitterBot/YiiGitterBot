package org.YiiCommunity.GitterBot.achievements;

import org.YiiCommunity.GitterBot.models.postgres.Achievement;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.postgres.User;

/**
 * Created by Alex on 10/20/15.
 */
public class FirstThankYou implements org.YiiCommunity.GitterBot.api.Achievement {
    private String name = "firstThankYou";

    @Override
    public void onUserChange(User user) {
        if (!user.hasAchievement(name))
            try {
                user.addAchievement(Achievement.getAchievement(name));
                Achievement obj = Achievement.getAchievement(name);
                Gitter.sendMessage(obj.getChatAnnounce().replace("{username}", user.getUsername()));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
