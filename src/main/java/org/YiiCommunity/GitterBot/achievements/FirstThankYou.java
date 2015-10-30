package org.YiiCommunity.GitterBot.achievements;

import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.models.database.Achievement;
import org.YiiCommunity.GitterBot.models.database.User;

/**
 * Created by Alex on 10/20/15.
 */
public class FirstThankYou extends org.YiiCommunity.GitterBot.api.Achievement {

    public FirstThankYou() {
        setCodeName("firstThankYou");
        setType(TYPE.ONE_TIME);
    }

    public void onUserChange(User user) {
        try {
            user.addAchievement(Achievement.getAchievement(getCodeName()));
            Achievement obj = Achievement.getAchievement(getCodeName());
            Gitter.sendMessage(obj.getChatAnnounce().replace("{username}", user.getUsername()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
