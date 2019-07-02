package app.service;

import java.util.Map;

public interface WeatherForecast {

    Map<String, String> getForecast(String city, String service);
}
