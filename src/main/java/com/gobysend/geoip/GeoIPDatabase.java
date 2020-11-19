package com.gobysend.geoip;

import com.gobysend.geoip.helpers.GobyResponse;
import com.maxmind.geoip2.DatabaseReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties("static")
public class GeoIPDatabase {

    private static final Logger logger = LogManager.getLogger(GeoIPDatabase.class);

    private static GeoIPDatabase instance;

    private Environment env;

    private DatabaseReader reader = null;

    public GeoIPDatabase(@Autowired Environment env) {
        this.env = env;

        this.init();
    }

    private void init() {
        try {
            String databasePath = this.env.getProperty("geoip.database.path", "");
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

    public static GeoIPDatabase getInstance() {
        return instance;
    }

    public DatabaseReader getReader() {
        return this.reader;
    }

}
