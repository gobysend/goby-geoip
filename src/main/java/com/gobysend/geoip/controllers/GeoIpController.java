package com.gobysend.geoip.controllers;

import com.gobysend.geoip.GeoIPDatabase;
import com.gobysend.geoip.helpers.GeoIPResponse;
import com.gobysend.geoip.helpers.GobyResponse;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

import static com.gobysend.geoip.controllers.ControllerConstants.PARAM_IP;
import static com.gobysend.geoip.controllers.ControllerConstants.PATH_RESOLVE_IP_TO_LOCATION;

@RestController
public class GeoIpController {

    private static final Logger logger = LogManager.getLogger(GeoIpController.class);

    private final GeoIPDatabase database;

    public GeoIpController(@Autowired GeoIPDatabase database) {
        this.database = database;
    }

    @GetMapping(value = PATH_RESOLVE_IP_TO_LOCATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public GobyResponse resolve(@RequestParam(value = PARAM_IP) String ip)
    {
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = this.database.getReader().city(ipAddress);

            Country country = response.getCountry();
            City city = response.getCity();
            Location location = response.getLocation();

            GeoIPResponse obj = new GeoIPResponse(country.getName(), country.getIsoCode(), city.getName(), location);

            return GobyResponse.success("Success", obj);
        }
        catch (AddressNotFoundException e) {
            logger.error("IP address not found.");
            return GobyResponse.failure("IP address not found.");
        }
        catch (Exception e) {
            logger.error(e);
            return GobyResponse.failure("Cannot resolve location for this IP address.");
        }
    }

}
