package org.YiiCommunity.GitterBot.models.postgres;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import lombok.Getter;
import lombok.Setter;
import org.YiiCommunity.GitterBot.api.DBModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alex on 10/21/15.
 */
@Entity
@Table(name = "Achievements")
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Getter
@Setter
public class Achievement implements Serializable, DBModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String key;
    private String title;
    private String description;
    private String chatAnnounce;

    @Transient
    public static Achievement getAchievement(String key) throws Exception {
        Achievement obj = Ebean.find(Achievement.class).where().eq("key", key).findUnique();
        if (obj != null) {
            return obj;
        }
        throw new Exception("Achievement not found! Fix ASAP!");
    }
}
