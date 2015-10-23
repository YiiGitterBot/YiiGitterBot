package org.YiiCommunity.GitterBot.achievements;

import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.postgres.User;

/**
 * Created by Alex on 10/20/15.
 */
public class FirstThankYou implements Achievement {
    private String name = "firstThankYou";

    @Override
    public void onUserChange(User user) {
        if (!user.hasAchievement(name))
            try {
                user.addAchievement(org.YiiCommunity.GitterBot.models.postgres.Achievement.getAchievement(name));
                org.YiiCommunity.GitterBot.models.postgres.Achievement obj = org.YiiCommunity.GitterBot.models.postgres.Achievement.getAchievement(name);
                Gitter.sendMessage(obj.getChatAnnounce().replace("{username}", user.getUsername()));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
