package org.YiiCommunity.GitterBot.api;

import lombok.Getter;
import lombok.Setter;
import org.YiiCommunity.GitterBot.models.postgres.User;

/**
 * Created by Alex on 10/20/15.
 */
public abstract class Achievement extends Configurable {
    public enum TYPE {
        ONE_TIME, MANY_TIMES;
    }

    @Getter
    private Achievement.TYPE type = TYPE.ONE_TIME;

    @Getter
    private String codeName;

    public Achievement() {
        setConfigurationsFolder("achievements");
    }

    public abstract void onUserChange(User user);

    protected void setType(TYPE type) {
        this.type = type;
    }

    protected void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
