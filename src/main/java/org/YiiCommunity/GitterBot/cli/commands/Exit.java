package org.YiiCommunity.GitterBot.cli.commands;

import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.utils.L;

public class Exit implements CLICommand {
    @Override
    public void onMessage(String text) {
        if (!text.equalsIgnoreCase("exit") && !text.equalsIgnoreCase("die") && !text.equalsIgnoreCase("stop")) return;

        L.$("Exiting");
        Gitter.broadcastMessage("GitterBot shutting down. Bye!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
