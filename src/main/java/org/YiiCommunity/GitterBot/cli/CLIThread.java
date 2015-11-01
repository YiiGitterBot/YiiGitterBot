package org.YiiCommunity.GitterBot.cli;

import jline.console.ConsoleReader;
import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.cli.commands.CLICommand;

import java.io.IOException;
import java.io.PrintWriter;

public class CLIThread extends Thread {
    public static ConsoleReader reader = null;
    public static PrintWriter out = null;

    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                for (CLICommand item : GitterBot.getInstance().getCliCommandListeners()) {
                    item.onMessage(line);
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
