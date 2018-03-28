package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration;
import eu.luminis.breed.sleuthzipkin.model.FriedBacon;
import eu.luminis.breed.sleuthzipkin.model.FriedEgg;
import eu.luminis.breed.sleuthzipkin.model.ToastedBread;
import eu.luminis.breed.sleuthzipkin.model.ToasterInformation;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "breakfast")
@CrossOrigin(origins = "http://localhost:8080")
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
  public ToastedBread getToastedBread() throws URISyntaxException {
    logger.info("Getting a toasted bread with information");
    ToasterInformation toasterInformation =
        restTemplate.getForObject(servicesConfiguration.getURIServiceB("breakfast/toastedbread"), ToasterInformation.class);//will initiate new span
    String preferredDoneness =
        restTemplate.getForObject(servicesConfiguration.getURIServiceC("breakfast/toastedbread"), String.class);//will initiate new span
    return new ToastedBread("80 degrees", toasterInformation.getPower(), toasterInformation.getCount(), preferredDoneness);//will return to the first span & finish the trace
  }

  @GetMapping("egg")
  public FriedEgg getFriedEgg() throws URISyntaxException {
    logger.info("Getting a fried egg with some information");
    String eggTemperature = restTemplate.getForObject(servicesConfiguration.getURIServiceB("breakfast/eggtemperature"), String.class);
    String countryOfEgg = restTemplate.getForObject(servicesConfiguration.getURIServiceC("breakfast/countryofegg"), String.class);
    return new FriedEgg(eggTemperature, countryOfEgg);
  }

  @GetMapping("bacon")
  public FriedBacon getFriedBacon() throws URISyntaxException {
    logger.info("Getting a fried egg with some information");
    String origin = restTemplate.getForObject(servicesConfiguration.getURIServiceD("breakfast/baconorigin"), String.class);
    return new FriedBacon(origin);
  }


}