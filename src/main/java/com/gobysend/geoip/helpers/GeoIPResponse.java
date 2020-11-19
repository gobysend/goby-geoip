package com.gobysend.geoip.helpers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maxmind.geoip2.record.Location;

public class GeoIPResponse {
    @JsonProperty("countryName")
    private String countryName;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("location")
    private Location location;

    @JsonCreator
    public GeoIPResponse(@JsonProperty("countryName") String countryName,
                         @JsonProperty("countryCode") String countryCode,
                         @JsonProperty("city") String city,
                         @JsonProperty("location") Location location)
    {
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.city = city;
        this.location = location;
    }
}
