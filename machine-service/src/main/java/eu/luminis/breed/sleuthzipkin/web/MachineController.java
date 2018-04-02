package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.Device;
import eu.luminis.breed.sleuthzipkin.GetEnergyResponse;
import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration;
import eu.luminis.breed.sleuthzipkin.model.MachineInformation;
import eu.luminis.breed.sleuthzipkin.soap.SoapServiceClient;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "machine")
public class MachineController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final RestTemplate restTemplate;
  private final ServicesConfiguration servicesConfiguration;
  private final SoapServiceClient soapServiceClient;

  @Autowired
  public MachineController(RestTemplate restTemplate, ServicesConfiguration servicesConfiguration, SoapServiceClient soapServiceClient) {
    this.restTemplate = restTemplate;
    this.servicesConfiguration = servicesConfiguration;
    this.soapServiceClient = soapServiceClient;
  }

  @GetMapping("toaster")//by calling the service, a trace & span is started
  public MachineInformation getToastInformation() throws URISyntaxException {
    logger.info("Getting information about power used & temperature of toaster");
    int temperature = restTemplate.getForObject(servicesConfiguration.getURITemperatureService("temperature/toaster"), Integer.class);//will initiate new span
    GetEnergyResponse energyResponse = soapServiceClient.getEnergyResponse(Device.TOASTER);
    logger.info("Returning information about power used & temperature");
    return new MachineInformation(energyResponse.getEnergy().getValue() + " " + energyResponse.getEnergy().getUnit(), temperature);//will return to the first span & finish the trace
  }

  @GetMapping("stove")//by calling the service, a trace & span is started
  public MachineInformation getStoveInformation() throws URISyntaxException {
    logger.info("Getting information about gas used & temperature of stove");
    int temperature = restTemplate.getForObject(servicesConfiguration.getURITemperatureService("temperature/stove"), Integer.class);//will initiate new span
    GetEnergyResponse energyResponse = soapServiceClient.getEnergyResponse(Device.STOVE);
    logger.info("Returning information about gas used & temperature");
    return new MachineInformation(energyResponse.getEnergy().getValue() + " " + energyResponse.getEnergy().getUnit(), temperature);//will return to the first span & finish the trace
  }

  @GetMapping("coffeemachine")//by calling the service, a trace & span is started
  public MachineInformation getCoffeeMachine() throws URISyntaxException {
    logger.info("Getting information about power used & temperature of coffeemachine");
    int temperature = restTemplate.getForObject(servicesConfiguration.getURITemperatureService("temperature/coffeemachine"), Integer.class);//will initiate new span
    GetEnergyResponse energyResponse = soapServiceClient.getEnergyResponse(Device.COFFEEMACHINE);
    logger.info("Returning information about power used & temperature");
    return new MachineInformation(energyResponse.getEnergy().getValue() + " " + energyResponse.getEnergy().getUnit(), temperature);//will return to the first span & finish the trace
  }
}
