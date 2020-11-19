package com.gobysend.geoip.helpers;

import com.maxmind.geoip2.DatabaseReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GeoIPDatabase {

    private static final Logger logger = LogManager.getLogger(GeoIPDatabase.class);

    private static GeoIPDatabase instance;

    private DatabaseReader reader = null;

    private GeoIPDatabase() {
        this.init();
    }

    private void init() {
        try {
            logger.info("Initializing GeoLite2 database...");

            Environment env = ContextProvider.getBean(Environment.class);
            String databasePath = env.getProperty("geoip.database.path");

            File database = new File(databasePath);
            if (!database.exists()) {
                logger.error("Cannot find GeoIP database file.");
                System.exit(0);
            }

            this.reader = new DatabaseReader.Builder(database).build();
        }
        catch (Exception e) {
            logger.error("Cannot read GeoIP database file.");
            System.exit(0);
        }
    }

    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    static {
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                instance = new GeoIPDatabase();
            }
        }, 0, 1, TimeUnit.DAYS);
    }

    public static GeoIPDatabase getInstance() {
        if (instance == null) {
            instance = new GeoIPDatabase();
        }
        return instance;
    }

    public DatabaseReader getReader() {
        return this.reader;
    }

}
