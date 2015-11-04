package org.YiiCommunity.GitterBot.containers;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.MatchingNamingConvention;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.dbplatform.IdType;
import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.loaders.ModuleLoader;
import org.YiiCommunity.GitterBot.utils.L;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataBaseContainer {
    public int maxConnections = 5;

    public DataSourceConfig dataSourceConfig;
    public ServerConfig serverConfig;
    public EbeanServer ebeanServer;

    public DataBaseContainer() {
        L.$("Initializing database...");
        initDataSourceConfig();
        initServerConfig();

        L.$("Connecting to database...");
        initEbeanServer();
    }

    public void initDataSourceConfig() {
        this.dataSourceConfig = new DataSourceConfig();
        this.dataSourceConfig.setDriver(GitterBot.getInstance().getConfiguration().getConfig().getString("database.driver"));
        this.dataSourceConfig.setUrl(GitterBot.getInstance().getConfiguration().getConfig().getString("database.url"));
        this.dataSourceConfig.setUsername(GitterBot.getInstance().getConfiguration().getConfig().getString("database.username"));
        this.dataSourceConfig.setPassword(GitterBot.getInstance().getConfiguration().getConfig().getString("database.password"));
        this.dataSourceConfig.setMinConnections(this.maxConnections);
        this.dataSourceConfig.setMaxConnections(this.maxConnections);
        this.dataSourceConfig.setWaitTimeoutMillis(1000 * 60);
    }

    public void initServerConfig() {
        this.serverConfig = new ServerConfig();

        this.serverConfig.setDataSourceConfig(this.dataSourceConfig);

        this.serverConfig.setName(this.sluggifyText("database:main"));
        this.serverConfig.setRegister(true);

        this.serverConfig.setDdlGenerate(false);
        this.serverConfig.setDdlRun(false);
        this.serverConfig.setDefaultServer(true);

        this.serverConfig.setNamingConvention(new MatchingNamingConvention());

        this.loadModels();

        DatabasePlatform dbPlatform = new DatabasePlatform();
        dbPlatform.getDbIdentity().setIdType(IdType.IDENTITY);
        dbPlatform.getDbIdentity().setSupportsGetGeneratedKeys(true);
        dbPlatform.getDbIdentity().setSupportsSequence(false);
        dbPlatform.getDbIdentity().setSupportsIdentity(true);
        this.serverConfig.setDatabasePlatform(dbPlatform);
    }

    private void loadModels() {
        Reflections reflections = new Reflections(new ArrayList<Object>() {{
            this.add("org.YiiCommunity.GitterBot");
            this.addAll(ModuleLoader.classes);
        }});
        List<Class<?>> list = new ArrayList<Class<?>>();

        Set<Class<? extends EntityBean>> annotated = reflections.getSubTypesOf(EntityBean.class);
        for (Class item : annotated) {
            try {
                list.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.serverConfig.setClasses(list);
    }

    public void initEbeanServer() {
        this.ebeanServer = EbeanServerFactory.create(this.serverConfig);
    }

    private String sluggifyText(String text) {
        text = text.toLowerCase();
        text = text.replaceAll("[^a-z0-9]+", "_");
        text = text.replaceAll("_$|^_", "");
        return text;
    }
}
