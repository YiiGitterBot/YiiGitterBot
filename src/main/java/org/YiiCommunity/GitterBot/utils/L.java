package org.YiiCommunity.GitterBot.utils;

import org.YiiCommunity.GitterBot.GitterBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Alex
 * Date: 03.02.14  23:22
 */
public class L {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static Logger log = LogManager.getLogger(GitterBot.class.getName());

    public static void $(Object o) {
        //System.out.println(o + ANSI_RESET);
        log.info(o.toString());
    }

    public static void $D(Object o) {
        if (!GitterBot.getInstance().isDebug()) return;
        System.out.println(o + ANSI_RESET);
        log.debug(o.toString());
    }
}
