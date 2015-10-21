package org.YiiCommunity.GitterBot;

import lombok.Getter;
import org.YiiCommunity.GitterBot.achievements.Achievement;
import org.YiiCommunity.GitterBot.containers.DataBaseContainer;
import org.YiiCommunity.GitterBot.utils.L;
import org.reflections.Reflections;

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
            debug = true;
        }

        ChatListener gitter = new ChatListener();

        gitter.streaming();
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
