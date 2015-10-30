package org.YiiCommunity.GitterBot.models.database;

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
@Table(name = "CarmaHistory")
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Getter
@Setter
public class CarmaHistory implements Serializable, DBModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long giverId;
    private Long timestamp;
    private Integer type;
    private String message;
}
