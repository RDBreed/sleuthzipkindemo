package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.soap.SoapServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "breakfast")
public class BreakfastController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final SoapServiceClient soapServiceClient;

  @Autowired
  public BreakfastController(SoapServiceClient soapServiceClient) {
    this.soapServiceClient = soapServiceClient;
  }

  @GetMapping("toastedbread")//by calling the service, a trace & span is started
  public String getToastPreference() throws InterruptedException {
    logger.info("Getting information how the bread should be toasted");
//    Thread.sleep(10000L);
    return "Medium";
  }

  @GetMapping("countryofegg")//by calling the service, a trace & span is started
  public String getEggInformation() {
    logger.info("Getting the country of the egg");
    return "This egg is from " + soapServiceClient.getCountryResponse().getCountry().getName();//will return to the first span & finish the trace
  }
}
