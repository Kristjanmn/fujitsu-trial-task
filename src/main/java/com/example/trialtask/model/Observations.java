package com.example.trialtask.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class Observations {
    private long timestamp;
    @JacksonXmlProperty(localName = "station")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Station> stations;

    @Data
    public static class Station {
        @JacksonXmlProperty
        private String name;
        @JacksonXmlProperty(localName = "wmocode")
        private int wmoCode;
        @JacksonXmlProperty
        private double longitude;
        @JacksonXmlProperty
        private double latitude;
        @JacksonXmlProperty
        private String phenomenon;
        @JacksonXmlProperty
        private double visibility;
        @JacksonXmlProperty
        private long precipitations;
        @JacksonXmlProperty(localName = "airpressure")
        private double airPressure;
        @JacksonXmlProperty(localName = "relativehumidity")
        private double relativeHumidity;
        @JacksonXmlProperty(localName = "airtemperature")
        private double airTemperature;
        @JacksonXmlProperty(localName = "winddirection")
        private long windDirection;
        @JacksonXmlProperty(localName = "windspeed")
        private double windSpeed;
        @JacksonXmlProperty(localName = "windspeedmax")
        private double windSpeedMax;
        @JacksonXmlProperty(localName = "waterlevel")
        private double waterLevel;
        @JacksonXmlProperty(localName = "waterlevel_eh2000")
        private double waterLevel_eh2000;
        @JacksonXmlProperty(localName = "watertemperature")
        private double waterTemperature;
        @JacksonXmlProperty(localName = "uvindex")
        private double uvIndex;
        @JacksonXmlProperty(localName = "sunshineduration")
        private double sunshineDuration;  // could be long, can't determine because weather system did not report it with cloudy weather.
        @JacksonXmlProperty(localName = "globalradiation")
        private double globalRadiation;   // same as above, not reported
    }
}
