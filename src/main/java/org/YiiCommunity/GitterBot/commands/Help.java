package org.YiiCommunity.GitterBot.commands;

import com.amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;
import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.Gitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10/23/15.
 */
public class Help extends Command {

    private List<String> commands = new ArrayList<>();

    public Help() {
        commands = getConfig().getStringList("commands");
    }

    @Override
    public void onMessage(MessageResponse message) {
        if (message.text.trim().equalsIgnoreCase("@" + GitterBot.getInstance().getConfiguration().getBotUsername())) {
            showHelp(message.fromUser.username);
            return;
        }
        for (String item : commands) {
            if (message.text.trim().equalsIgnoreCase("@" + GitterBot.getInstance().getConfiguration().getBotUsername() + " " + item)) {
                showHelp(message.fromUser.username);
                return;
            }
        }
    }

    private void showHelp(String username) {
        try {
            Gitter.sendMessage("" +
                    "Привет, @" + username + "!\n" +
                    "Меня зовут *ВАЛЛИ*. Я - автоматическая система поддакивания.\n" +
                    "Вот что я умею:\n" +
                    "* Если ты упомянешь пользователя и напишешь \"спасибо\", то ему в карму прилетит бонус\n" +
                    "* Если ты напишешь \"карма\" или \"карма @ник\" то я покажу тебе твою карму или указаного пользователя\n" +
                    "* По команде ping я отвечу pong\n" +
                    "* Если спросишь \"Кто на свете всех милее?\" то я покажу самого закармованного юзера\n" +
                    "\n" +
                    "Я написан @Alex-Bond и @lavrentiev" +
                    "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
