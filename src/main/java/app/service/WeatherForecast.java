package app.service;

import java.io.FileNotFoundException;
import java.util.Map;

public interface WeatherForecast {

    Map<String, String> getForecast(String city);
}
