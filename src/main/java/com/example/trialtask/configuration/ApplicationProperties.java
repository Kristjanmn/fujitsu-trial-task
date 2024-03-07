package com.example.trialtask.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
//@PropertySource(value = "classpath:application.yml", encoding = "UTF-8")
public class ApplicationProperties {
    private WeatherPhenomenon weatherPhenomenon;
    private String weatherCron;
    private String weatherDataUri;

    @Data
    public static class WeatherPhenomenon {
        private List<String> snowOrSleet;
        private List<String> rain;
        private List<String> forbidden;
    }
}
