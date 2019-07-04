package app;

import app.service.ServiceRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("city_name")
@Controller
public class MainController {

    @Autowired
    private ServiceRouter serviceRouter;

    @GetMapping()
    public String mainPage() {
        return "forecast";
    }

    @PostMapping()
    public String getWeather(@RequestParam(name = "city_name", required = false) String city,
                             @RequestParam(name = "service_name", required = false) String service,
                             Model model) {
        model.addAllAttributes(serviceRouter.getForecast(city, service));
        model.addAttribute("city_name", city);
        return "forecast";
    }
}
