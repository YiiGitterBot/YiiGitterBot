package org.YiiCommunity.GitterBot;

import com.google.common.base.Predicate;
import jline.console.ConsoleReader;
import lombok.Getter;
import org.YiiCommunity.GitterBot.api.Achievement;
import org.YiiCommunity.GitterBot.api.Command;
import org.YiiCommunity.GitterBot.cli.CLIThread;
import org.YiiCommunity.GitterBot.cli.commands.CLICommand;
import org.YiiCommunity.GitterBot.containers.DataBaseContainer;
import org.YiiCommunity.GitterBot.loaders.LibrariesLoader;
import org.YiiCommunity.GitterBot.loaders.ModuleLoader;
import org.YiiCommunity.GitterBot.utils.L;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Getter
public class GitterBot {
    @Getter
    private static GitterBot instance;
    private boolean debug = false;
    private Configuration configuration;
    private ArrayList<Achievement> achievementsListeners = new ArrayList<>();
    private ArrayList<Command> commandListeners = new ArrayList<>();
    private ArrayList<CLICommand> cliCommandListeners = new ArrayList<>();

    /**
     * @param args the command line arguments
     * @throws Exception
     */
    public GitterBot(String[] args) throws Exception {
        try {
            instance = this;

            L.$("Yii Gitter Bot ... " + L.ANSI_CYAN + "[START]");

            if (args.length > 0) {
                Level level = Level.DEBUG;
                if (Arrays.asList(args).contains("trace"))
                    level = Level.TRACE;
                if (Arrays.asList(args).contains("all"))
                    level = Level.ALL;
                Configurator.setRootLevel(level);
                Configurator.setLevel("GitterBot", level);
                debug = true;
            }

            loadCLI();

            new LibrariesLoader().load();

            loadConfiguration();
            connectToDatabase();

            new ModuleLoader().load();

            loadAchievementsListeners();
            loadCommandsListeners();
            loadCLICommandsListeners();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    L.$("Aggressive shutting down!");
                }
            });

            ChatListener gitter = new ChatListener();

            gitter.startListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCLI() {
        try {
            ConsoleReader reader = new ConsoleReader(System.in, System.out);
            reader.setExpandEvents(false);
            CLIThread.reader = reader;
            CLIThread.out = new PrintWriter(CLIThread.reader.getOutput());

            new CLIThread().start();
        } catch (Exception e) {
            e.printStackTrace();
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

    private void loadCLICommandsListeners() {
        Reflections reflections = new Reflections("org.YiiCommunity.GitterBot.cli.commands");

        Set<Class<? extends CLICommand>> annotated = reflections.getSubTypesOf(CLICommand.class);
        for (Class item : annotated) {
            try {
                cliCommandListeners.add((CLICommand) item.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void connectToDatabase() {
        new DataBaseContainer();
    }
}
