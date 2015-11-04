package org.YiiCommunity.GitterBot.api;

import com.amatkivskiy.gitter.rx.sdk.model.response.room.RoomResponse;
import lombok.Getter;
import org.YiiCommunity.GitterBot.models.database.User;

public abstract class Achievement extends Configurable {
    public enum TYPE {
        ONE_TIME, MANY_TIMES;
    }

    @Getter
    private Achievement.TYPE type = TYPE.ONE_TIME;

    @Getter
    private String codeName;

    public abstract void onUserChange(RoomResponse room, User user);

    protected void setType(TYPE type) {
        this.type = type;
    }

    protected void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
