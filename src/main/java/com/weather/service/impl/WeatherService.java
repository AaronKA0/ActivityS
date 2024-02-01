package com.weather;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService implements IWeatherService {
    private final String apiKey = "CWA-22E2C648-BEAF-4778-A564-C1749104E966";
    private final String apiUrl = "https://opendata.cwa.gov.tw/api/v1/rest/datastore/F-C0032-001";

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String getWeather(String locationName) {
        String url = apiUrl + "?Authorization=" + apiKey + "&locationName=" + locationName;
        return restTemplate.getForObject(url, String.class);
    }

}
