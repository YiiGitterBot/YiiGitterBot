package org.YiiCommunity.GitterBot.models.json;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Alex on 10/20/15.
 */
@Getter
@Setter
public class MessageSender {
    private String id;
    private String username;
    private String displayName;
    private String url;
    private String avatarUrlSmall;
    private String avatarUrlMedium;
    private Integer gv;
    private Integer v;
}
