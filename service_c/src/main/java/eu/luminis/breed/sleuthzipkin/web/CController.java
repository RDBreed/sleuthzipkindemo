package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.soap.SoapServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "c")
public class CController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final SoapServiceClient soapServiceClient;

  @Autowired
  public CController(SoapServiceClient soapServiceClient) {
    this.soapServiceClient = soapServiceClient;
  }

  @GetMapping//by calling the service, a trace & span is started
  public String getCModel() {
    logger.info("Getting a cmodel");
    return "this is service c" + soapServiceClient.getCountryResponse().getCountry().getName();//will return to the first span & finish the trace
  }

  @GetMapping(path = "performance")//by calling the service, a trace & span is started
  public String getCPerformance() throws InterruptedException {
    logger.info("Getting a cmodel");
    Thread.sleep(30000L);
    return "this is service c";//will return to the first span & finish the trace
  }
}
