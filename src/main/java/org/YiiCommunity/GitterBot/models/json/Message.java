package org.YiiCommunity.GitterBot.models.json;

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
    private ArrayList<Mention> mentions;
    private MessageSender fromUser;
}
