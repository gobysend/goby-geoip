package com.gobysend.geoip.helpers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GobyResponse {
    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;

    @JsonCreator
    public GobyResponse(@JsonProperty("code") int code,
                        @JsonProperty("message") String message,
                        @JsonProperty("data") Object data)
    {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static GobyResponse failure(String message, Object data) {
        return new GobyResponse(HttpStatus.BAD_REQUEST.value(), message, data);
    }

    public static GobyResponse failure(String message) {
        return new GobyResponse(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    public static GobyResponse success(String message, Object data) {
        return new GobyResponse(HttpStatus.OK.value(), message, data);
    }

    @JsonIgnore
    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return code + " - " + message + " - " + data;
    }
}
