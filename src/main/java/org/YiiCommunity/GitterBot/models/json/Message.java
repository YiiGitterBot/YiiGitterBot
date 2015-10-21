package org.YiiCommunity.GitterBot.models.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Created by Alex on 10/20/15.
 */
@Getter
@Setter
public class Message {
    private String id;
    private String text;
    private String html;
    private String sent;
    private String editedAt;
    private MessageSender fromUser;
    private boolean unread;
    private Integer readBy;
    @JsonIgnore
    private String urls;
    private ArrayList<Mention> mentions;
    private ArrayList<Issue> issues;
    @JsonIgnore
    private String meta;
    private Integer v;
}
