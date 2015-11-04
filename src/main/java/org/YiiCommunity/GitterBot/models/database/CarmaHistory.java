package org.YiiCommunity.GitterBot.models.database;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CarmaHistory")
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Getter
@Setter
public class CarmaHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long giverId;
    private Long timestamp;
    private Integer type;
    private String message;

    @Transient
    public static Long getLastThankYou(User from, User to) {
        CarmaHistory obj = Ebean.find(CarmaHistory.class).where().eq("userId", to.getId()).eq("giverId", from.getId()).orderBy("timestamp DESC").setMaxRows(1).findUnique();
        if (obj == null) {
            return 0L;
        }
        return obj.getTimestamp();
    }
}
