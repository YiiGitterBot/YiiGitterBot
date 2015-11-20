package org.YiiCommunity.GitterBot.models.gitter;

/**
 * Created by alex on 11/20/15.
 */
public class UserResponse extends com.amatkivskiy.gitter.sdk.model.response.UserResponse {
    public UserResponse(String id, Integer v, String username, String avatarUrlSmall, String gv, String displayName, String url, String avatarUrlMedium) {
        super(id, v, username, avatarUrlSmall, gv, displayName, url, avatarUrlMedium);
    }
}
