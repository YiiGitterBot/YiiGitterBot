package org.YiiCommunity.GitterBot.cli.commands;

import org.YiiCommunity.GitterBot.cli.CLIThread;

import java.io.IOException;

public class Clean implements CLICommand {
    @Override
    public void onMessage(String text) {
        if (!text.equalsIgnoreCase("clear") && !text.equalsIgnoreCase("clean")) return;

        try {
            CLIThread.reader.clearScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
