package org.YiiCommunity.GitterBot.models.database;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Achievements")
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Getter
@Setter
public class Achievement implements Serializable {
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
