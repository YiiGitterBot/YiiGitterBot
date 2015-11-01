package org.YiiCommunity.GitterBot.cli.commands;

public interface CLICommand {
    public void onMessage(String text);
}
