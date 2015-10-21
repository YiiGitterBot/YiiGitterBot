package org.YiiCommunity.GitterBot.models.postgres;

import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import lombok.Getter;
import lombok.Setter;
import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.achievements.Achievement;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alex on 10/20/15.
 */
@Entity
@Table(name = "Users")
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

    @Transient
    public static User getUser(String username) {
        User out = new User();
        out.setUsername(username);
        return out;
    }

    @Transient
    public void updateAchievements() {
        for (Achievement item : GitterBot.getInstance().getAchievementsListeners()) {
            item.onUserChange(this);
        }
    }

    @Transient
    public boolean hasAchievement(String name) {
        return false;
    }
}
