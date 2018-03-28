package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration;
import eu.luminis.breed.sleuthzipkin.model.AModel;
import eu.luminis.breed.sleuthzipkin.model.BModel;
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
@RequestMapping(path = "a")
@CrossOrigin(origins = "http://localhost:8080")
public class AController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final RestTemplate restTemplate;
  private final ServicesConfiguration servicesConfiguration;

  @Autowired
  public AController(RestTemplate restTemplate, ServicesConfiguration servicesConfiguration) {
    this.restTemplate = restTemplate;
    this.servicesConfiguration = servicesConfiguration;
  }

  @GetMapping//by calling the service, a trace & span is started
  public AModel getAModel() throws URISyntaxException {
    logger.info("Getting a amodel");
    BModel bModel = restTemplate.getForObject(servicesConfiguration.getURIServiceB("b"), BModel.class);//will initiate new span
    String cValue = restTemplate.getForObject(servicesConfiguration.getURIServiceC("c"), String.class);//will initiate new span
    return new AModel("this is service a", bModel.getB(), cValue, bModel.getD());//will return to the first span & finish the trace
  }

  /**
   * Use to demonstrate the tracing of an error in service d
   */
  @GetMapping(path = "error")
  public AModel getAmodelButError() throws URISyntaxException {
    logger.info("Getting a amodel");
    BModel bModel = restTemplate.getForObject(servicesConfiguration.getURIServiceB("b/error"), BModel.class);//will initiate new span
    String cValue = restTemplate.getForObject(servicesConfiguration.getURIServiceC("c"), String.class);//will initiate new span
    return new AModel("this is service a", bModel.getB(), cValue, bModel.getD());//will return to the first span & finish the trace
  }

  /**
   * If you do not peek into the other services, you can find which service is having a performance issue with the help of zipkin...
   */
  @GetMapping(path = "performance")
  public AModel getAModelWithPerformanceLag() throws URISyntaxException {
    logger.info("Getting a amodel");
    BModel bModel = restTemplate.getForObject(servicesConfiguration.getURIServiceB("b/performance"), BModel.class);//will initiate new span
    String cValue = restTemplate.getForObject(servicesConfiguration.getURIServiceC("c/performance"), String.class);//will initiate new span
    return new AModel("this is service a", bModel.getB(), cValue, bModel.getD());//will return to the first span & finish the trace
  }


}
