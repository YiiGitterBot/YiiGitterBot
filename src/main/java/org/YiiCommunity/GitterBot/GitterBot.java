package org.YiiCommunity.GitterBot;

import lombok.Getter;
import org.YiiCommunity.GitterBot.api.Achievement;
import org.YiiCommunity.GitterBot.containers.DataBaseContainer;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.utils.L;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.util.ArrayList;
import java.util.Set;

public class GitterBot {

    @Getter
    private static GitterBot instance;
    @Getter
    private boolean debug = false;
    @Getter
    private Configuration configuration;
    @Getter
    private ArrayList<Achievement> achievementsListeners = new ArrayList<>();

    /**
     * @param args the command line arguments
     * @throws Exception
     */
    public GitterBot(String[] args) throws Exception {
        instance = this;

        L.$("Yii Gitter Bot ... [START]");

        loadConfiguration();
        connectToDatabase();
        loadAchievementsListeners();

        if (args.length > 0) {
            Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.DEBUG);
            debug = true;
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Gitter.sendMessage("GitterBot shutting down. Bye!");
                } catch (Exception e) {
                }
            }
        });

        ChatListener gitter = new ChatListener();

        gitter.startListening();
    }

    private void loadConfiguration() {
        configuration = new Configuration();

        L.$("Configuration loaded");
    }

    private void loadAchievementsListeners() {
        Reflections reflections = new Reflections("org.YiiCommunity.GitterBot.achievements");

        Set<Class<? extends Achievement>> annotated = reflections.getSubTypesOf(Achievement.class);
        for (Class item : annotated) {
            try {
                achievementsListeners.add((Achievement) item.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void connectToDatabase() {
        new DataBaseContainer();
    }
}
