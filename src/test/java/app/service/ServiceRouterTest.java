package app.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ServiceRouterTest {


    ServiceRouter serviceRouter;
    OpenWeatherMapWeatherService openWeatherMapWeatherService;
    APIXUWeatherService apixuWeatherService;

    String apixuServiceName;
    String openweathermapServiceName;
    String cityDefault;
    String emptyCity;
    String emptyService;
    String incorrectCityName;

    @Before
    public void setUp() {
        apixuWeatherService = new APIXUWeatherService();
        openWeatherMapWeatherService = new OpenWeatherMapWeatherService();
        serviceRouter = new ServiceRouter(apixuWeatherService, openWeatherMapWeatherService);
        apixuServiceName = "apixu";
        openweathermapServiceName = "openweathermap";
        cityDefault = "Moscow";
        emptyCity = "";
        emptyService = "";
        incorrectCityName = "1111";
    }

    @Test
    public void WhenCityOrServiceIsEmptyItReturnsMapWithMistakeMessageInKeyT() {
        Map<String, String> map = new HashMap<>();
        map.put("t", "Enter city name or service");
        assertEquals(map, serviceRouter.getForecast(emptyCity, emptyService));
    }

    @Test
    public void WhenCityIsIncorrectItReturnsMapWithMistakeMessageInKeyT() {
        Map<String, String> map = new HashMap<>();
        map.put("t", "Enter correct city name or this city is unknown for service");
        assertEquals(map, serviceRouter.getForecast(incorrectCityName, apixuServiceName));
        assertEquals(map, serviceRouter.getForecast(incorrectCityName, openweathermapServiceName));
    }

    @Test
    public void WhenCityAndServiceCorrectItReturnMapWithData() {
        ReflectionTestUtils.setField(apixuWeatherService, "apixuKey", "e367dddb707143d6a11182604190207");

        Map<String, String> resultMap;
        resultMap = serviceRouter.getForecast(cityDefault, apixuServiceName);
        assertNotNull(resultMap.get("city"));
        assertNotNull(resultMap.get("t"));
        assertNotNull(resultMap.get("h"));
        assertNotNull(resultMap.get("p"));

        ReflectionTestUtils.setField(openWeatherMapWeatherService, "openweathermap_key", "5db4458c1ed074f12534841cc90cfbeb");
        resultMap = serviceRouter.getForecast(cityDefault, openweathermapServiceName);
        assertNotNull(resultMap.get("city"));
        assertNotNull(resultMap.get("t"));
        assertNotNull(resultMap.get("h"));
        assertNotNull(resultMap.get("p"));
    }
}