package org.YiiCommunity.GitterBot.models.database;

import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import lombok.Getter;
import lombok.Setter;
import org.YiiCommunity.GitterBot.api.DBModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserAchievements")
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Getter
@Setter
public class UserAchievements implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long timestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "achievementId")
    private Achievement achievement;
}
