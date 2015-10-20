package org.YiiCommunity.GitterBot.containers;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.AutofetchConfig;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.MatchingNamingConvention;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.dbplatform.IdType;
import org.YiiCommunity.GitterBot.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 1/19/15.
 */
public class DataBaseContainer {
    public int maxConnections = 50;

    public DataSourceConfig dataSourceConfig;
    public AutofetchConfig autofetchConfig;
    public ServerConfig serverConfig;
    public EbeanServer ebeanServer;

    public DataBaseContainer() {
        L.$("Initializing database...");
        initDataSourceConfig();
        initAutofetchConfig();
        initServerConfig();

        L.$("Connecting to database...");
        initEbeanServer();
    }

    public void initDataSourceConfig() {
        this.dataSourceConfig = new DataSourceConfig();
        this.dataSourceConfig.setDriver("com.mysql.jdbc.Driver");
        this.dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/DATABASE_NAME");
        this.dataSourceConfig.setUsername("root");
        this.dataSourceConfig.setPassword("CHANGE_PASSWORD");
        this.dataSourceConfig.setMinConnections(this.maxConnections);
        this.dataSourceConfig.setMaxConnections(this.maxConnections);
        this.dataSourceConfig.setWaitTimeoutMillis(1000 * 60);
    }

    public void initAutofetchConfig() {
        this.autofetchConfig = new AutofetchConfig();
        this.autofetchConfig.setLogDirectory(".");
    }

    public void initServerConfig() {
        this.serverConfig = new ServerConfig();

        this.serverConfig.setDataSourceConfig(this.dataSourceConfig);
        this.serverConfig.setAutofetchConfig(this.autofetchConfig);

        this.serverConfig.setName(this.sluggifyText("mysql:maindatabase"));
        this.serverConfig.setRegister(true);

        this.serverConfig.setDdlGenerate(false);
        this.serverConfig.setDdlRun(false);
        this.serverConfig.setDefaultServer(true);

        this.serverConfig.setNamingConvention(new MatchingNamingConvention());

        List<Class<?>> list = new ArrayList<Class<?>>() {{
            //add(Diamond.class);
        }};

        this.serverConfig.setClasses(list);

        DatabasePlatform dbPlatform = new DatabasePlatform();
        dbPlatform.getDbIdentity().setIdType(IdType.IDENTITY);
        dbPlatform.getDbIdentity().setSupportsGetGeneratedKeys(true);
        dbPlatform.getDbIdentity().setSupportsSequence(false);
        dbPlatform.getDbIdentity().setSupportsIdentity(true);
        this.serverConfig.setDatabasePlatform(dbPlatform);
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
