package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration;
import eu.luminis.breed.sleuthzipkin.model.Coffee;
import eu.luminis.breed.sleuthzipkin.model.FriedBacon;
import eu.luminis.breed.sleuthzipkin.model.FriedEgg;
import eu.luminis.breed.sleuthzipkin.model.MachineInformation;
import eu.luminis.breed.sleuthzipkin.model.ToastedBread;
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
@CrossOrigin(origins = {"http://localhost:8080", "https://localhost"})
public class BreakfastController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final RestTemplate restTemplate;
  private final ServicesConfiguration servicesConfiguration;
  private static final String TEMPERATURE_UNIT = " degrees";

  @Autowired
  public BreakfastController(RestTemplate restTemplate, ServicesConfiguration servicesConfiguration) {
    this.restTemplate = restTemplate;
    this.servicesConfiguration = servicesConfiguration;
  }

  @GetMapping("toastedbread")//by calling the service, a trace & span is started
  public ToastedBread getToastedBread() throws URISyntaxException {
    logger.info("Getting a toasted bread with information");
    MachineInformation toasterInformation =
        restTemplate.getForObject(servicesConfiguration.getURIMachineService("machine/toaster"), MachineInformation.class);//will initiate new span
    String preferredDoneness =
        restTemplate.getForObject(servicesConfiguration.getURIPreferenceService("preference/toastedbread"), String.class);//will initiate new span
    logger.info("Returning toasted bread");
    return new ToastedBread(toasterInformation.getTemperature() + TEMPERATURE_UNIT, toasterInformation.getPower(), preferredDoneness);//will return to the first span & finish the trace
  }

  @GetMapping("egg")
  public FriedEgg getFriedEgg() throws URISyntaxException {
    logger.info("Getting a fried egg with some information");
    MachineInformation machineInformation = restTemplate.getForObject(servicesConfiguration.getURIMachineService("machine/stove"), MachineInformation.class);
    String preference = restTemplate.getForObject(servicesConfiguration.getURIPreferenceService("preference/egg"), String.class);
    logger.info("Returning fried egg");
    return new FriedEgg(machineInformation.getTemperature() + TEMPERATURE_UNIT, machineInformation.getPower(), preference);
  }

  @GetMapping("bacon")
  public FriedBacon getFriedBacon() throws URISyntaxException {
    logger.info("Getting a fried bacon with some information");
    String preference = restTemplate.getForObject(servicesConfiguration.getURIPreferenceService("preference/bacon"), String.class);
    logger.info("Returning fried bacon");
    return new FriedBacon(preference);
  }

  @GetMapping("coffee")
  public Coffee getCoffee() throws URISyntaxException {
    logger.info("Getting a coffee with information");
    MachineInformation machineInformation =
        restTemplate.getForObject(servicesConfiguration.getURIMachineService("machine/coffeemachine"), MachineInformation.class);//will initiate new span
    String preferredDoneness =
        restTemplate.getForObject(servicesConfiguration.getURIPreferenceService("preference/coffee"), String.class);//will initiate new span
    logger.info("Returning toasted bread");
    return new Coffee(machineInformation.getTemperature() + TEMPERATURE_UNIT, machineInformation.getPower(), preferredDoneness);//will return to the first span & finish the trace
  }


}