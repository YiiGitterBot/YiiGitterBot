package org.YiiCommunity.GitterBot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static Logger log = LoggerFactory.getLogger("GitterBot");

    public static void $(Object o) {
        log.info(o.toString() + L.ANSI_RESET);
    }

    public static void $D(Object o) {
        log.debug(o.toString() + L.ANSI_RESET);
    }
}
