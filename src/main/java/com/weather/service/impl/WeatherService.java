package com.weather.service.impl;

import com.weather.service.IGoogleMapService;
import com.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService implements IWeatherService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IGoogleMapService googleMapService;
    private final String apiKey = "7b3fb20082613d42fef1751fca816659";

    @Override
    public String getWeather(String locationName) {

        double[] latLng = googleMapService.getLatLon(locationName);

        if (latLng == null) {
            return null;
        }

        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latLng[0] + "&lon=" + latLng[1] + "&appid=" + apiKey + "&units=metric&lang=ZH_TW";

        String res = restTemplate.getForObject(apiUrl, String.class);

        if (res != null) {
            return res;
        } else {
            return null;
        }
    }

}
