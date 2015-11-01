package org.YiiCommunity.GitterBot;

import com.google.common.base.Predicate;
import lombok.Getter;
import org.YiiCommunity.GitterBot.api.Achievement;
import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.containers.DataBaseContainer;
import org.YiiCommunity.GitterBot.containers.Gitter;
import org.YiiCommunity.GitterBot.loaders.LibrariesLoader;
import org.YiiCommunity.GitterBot.loaders.ModuleLoader;
import org.YiiCommunity.GitterBot.utils.L;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.util.ArrayList;
import java.util.Set;

@Getter
public class GitterBot {
    @Getter
    private static GitterBot instance;
    private boolean debug = false;
    private Configuration configuration;
    private ArrayList<Achievement> achievementsListeners = new ArrayList<>();
    private ArrayList<Command> commandListeners = new ArrayList<>();

    /**
     * @param args the command line arguments
     * @throws Exception
     */
    public GitterBot(String[] args) throws Exception {
        try {
            instance = this;

            L.$("Yii Gitter Bot ... " + L.ANSI_CYAN + "[START]");

            new LibrariesLoader().load();

            loadConfiguration();
            connectToDatabase();

            new ModuleLoader().load();

            loadAchievementsListeners();
            loadCommandsListeners();

            if (args.length > 0) {
                Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
                root.setLevel(Level.DEBUG);
                debug = true;
            }

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    Gitter.sendMessage("GitterBot shutting down. Bye!");
                }
            });

            ChatListener gitter = new ChatListener();

            gitter.startListening();
        } catch (Exception e) {
            L.$("New exception:" + e.getMessage());
        }
    }

    private void loadConfiguration() {
        configuration = new Configuration();

        L.$("Configuration loaded");
    }

    private void loadAchievementsListeners() {
        Reflections reflections = new Reflections(ConfigurationBuilder.build(ModuleLoader.classes, (Predicate<String>) input -> input.endsWith(".class")));

        Set<Class<? extends Achievement>> annotated = reflections.getSubTypesOf(Achievement.class);
        for (Class item : annotated) {
            try {
                achievementsListeners.add((Achievement) item.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCommandsListeners() {
        Reflections reflections = new Reflections(ConfigurationBuilder.build(ModuleLoader.classes, (Predicate<String>) input -> input.endsWith(".class")));

        Set<Class<? extends Command>> annotated = reflections.getSubTypesOf(Command.class);
        for (Class item : annotated) {
            try {
                commandListeners.add((Command) item.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void connectToDatabase() {
        new DataBaseContainer();
    }
}
