package eu.luminis.breed.sleuthzipkin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "preference")
public class PreferenceController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("toastedbread")//by calling the service, a trace & span is started
    public String getToastPreference() throws InterruptedException {
        logger.info("Getting information how the bread should be toasted");
        Thread.sleep(5000L);
        return "Medium";
    }

    @GetMapping("egg")//by calling the service, a trace & span is started
    public String getEggInformation() {
        logger.info("Getting information about preference of egg");
        return "Fried egg, medium baked";//will return to the first span & finish the trace
    }

    @GetMapping("bacon")//by calling the service, a trace & span is started
    public String getBaconPreference() {
        logger.info("Getting information about preference of bacon");
        return "Crispy";//will return to the first span & finish the trace
    }

    @GetMapping("coffee")//by calling the service, a trace & span is started
    public String getCoffeePreference() {
        logger.info("Getting information about preference of coffee");
        return "Regular, no sugar or milk";//will return to the first span & finish the trace
    }

    @GetMapping("health")
    public boolean getHealth() {
        return true;
    }
}
