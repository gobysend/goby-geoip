# goby-geoip
Resolve IP to location

# To build application, please install Maven and trigger following command
```bash
mvn install
mvn clean package
```

After that, a file will be built under target folder: `target/geoip-1.0.0.jar`

# Run application with command line
```bash
java -jar geoip-1.0.0.jar --spring.config.location=file:///path/to/application.properties
```

# `application.properties` looks like this
```text
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration, org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration

# Path to GeoLite2 database
geoip.database.path=/path/to/GeoLite2-City.mmdb
```

# Weekly update database
Just need to download database from MaxMind.com and put to the path specified in `application.properties`. Then the application will automatically load new changes (daily refresh).

