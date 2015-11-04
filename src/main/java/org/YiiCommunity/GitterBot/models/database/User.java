package org.YiiCommunity.GitterBot.models.database;

import com.amatkivskiy.gitter.rx.sdk.model.response.room.RoomResponse;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import lombok.Getter;
import lombok.Setter;
import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.api.Achievement;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Users")
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    private List<UserAchievements> achievementsCache = null;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Long lastMessageTimestamp;
    private Integer carma = 0;
    private Integer thanks = 0;
    private Long messagesCount = 0L;

    @Transient
    public static User getUser(String name) {
        User out = Ebean.find(User.class).where().eq("username", name).findUnique();
        if (out != null) {
            return out;
        }
        out = new User();
        out.setUsername(name);
        out.setLastMessageTimestamp(System.currentTimeMillis() / 1000);
        Ebean.save(out);
        return out;
    }

    @Transient
    public void updateAchievements(RoomResponse room) {
        for (Achievement item : GitterBot.getInstance().getAchievementsListeners()) {
            if (item.getType() == Achievement.TYPE.MANY_TIMES || !this.hasAchievement(item.getCodeName()))
                item.onUserChange(room, this);
        }
    }

    @Transient
    public void addAchievement(org.YiiCommunity.GitterBot.models.database.Achievement achievement) {
        UserAchievements obj = new UserAchievements();
        obj.setUserId(this.id);
        obj.setAchievement(achievement);
        obj.setTimestamp(System.currentTimeMillis() / 1000);
        Ebean.save(obj);
        loadAchievements();
    }

    @Transient
    public boolean hasAchievement(String key) {
        for (UserAchievements item : getAchievements()) {
            if (item.getAchievement().getKey().equals(key))
                return true;
        }
        return false;
    }

    @Transient
    public List<UserAchievements> getAchievements() {
        if (achievementsCache == null) {
            loadAchievements();
        }
        return achievementsCache;
    }

    @Transient
    private void loadAchievements() {
        achievementsCache = Ebean.find(UserAchievements.class).where().eq("userId", this.id).findList();
    }

    @Transient
    public void changeCarma(Integer value, User giver, String message, RoomResponse room) {
        this.setCarma(this.getCarma() + value);
        Ebean.update(this);
        CarmaHistory obj = new CarmaHistory();
        obj.setUserId(this.id);
        obj.setGiverId(giver.getId());
        obj.setType(value);
        obj.setRoom(room.url);
        obj.setMessage(message);
        obj.setTimestamp(System.currentTimeMillis() / 1000);
        Ebean.save(obj);
        giver.setThanks(giver.getThanks() + 1);
        Ebean.save(giver);
    }
}
