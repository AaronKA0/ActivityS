package com.weather.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weather.service.IGoogleMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleMapService implements IGoogleMapService {
    @Autowired
    private RestTemplate restTemplate;
    private final String apiKey = "AIzaSyBjHlN8i2-4cw4QDuaPgyctq7OX1n6c6ls";
    @Override
    public double[] getLatLon(String locationName) {

        String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + locationName + "&key=" + apiKey;

        String res = restTemplate.getForObject(apiUrl, String.class);

        if (res != null) {
            JSONObject jsonObject = JSON.parseObject(res);
            JSONArray results = jsonObject.getJSONArray("results");

            if (results.size() > 0) {
                JSONObject location = results.getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                return new double[]{lat, lng};
            }
        }
        return null;
    }
}
