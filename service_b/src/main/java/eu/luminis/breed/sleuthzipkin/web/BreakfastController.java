package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration;
import eu.luminis.breed.sleuthzipkin.model.ToasterInformation;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "breakfast")
public class BreakfastController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final RestTemplate restTemplate;
  private final ServicesConfiguration servicesConfiguration;

  @Autowired
  public BreakfastController(RestTemplate restTemplate, ServicesConfiguration servicesConfiguration) {
    this.restTemplate = restTemplate;
    this.servicesConfiguration = servicesConfiguration;
  }

  @GetMapping("toastedbread")//by calling the service, a trace & span is started
  public ToasterInformation getToastInformation() throws URISyntaxException {
    logger.info("Getting information about power used & total count of toasts made");
    int count = restTemplate.getForObject(servicesConfiguration.getURIServiceD("breakfast/totaltoasted"), Integer.class);//will initiate new span
    logger.info("Returning information about power used & total count of toasts made");
    return new ToasterInformation("1500 kWh", count);//will return to the first span & finish the trace
  }

  @GetMapping("eggtemperature")//by calling the service, a trace & span is started
  public String getEggTemperature() {
    logger.info("Getting information temperature of fried egg");
    return "80 degrees";//will return to the first span & finish the trace
  }
}
