package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration;
import eu.luminis.breed.sleuthzipkin.model.BModel;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "b")
public class BController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final RestTemplate restTemplate;
  private final ServicesConfiguration servicesConfiguration;

  @Autowired
  public BController(RestTemplate restTemplate, ServicesConfiguration servicesConfiguration) {
    this.restTemplate = restTemplate;
    this.servicesConfiguration = servicesConfiguration;
  }

  @GetMapping//by calling the service, a trace & span is started
  public BModel getBModel() throws URISyntaxException {
    logger.info("Getting a bmodel");
    String dValue = restTemplate.getForObject(servicesConfiguration.getURIServiceD("d"), String.class);//will initiate new span
    return new BModel("this is service b", dValue);//will return to the first span & finish the trace
  }

  @GetMapping(path = "error")//by calling the service, a trace & span is started
  public BModel getBModelButError() throws URISyntaxException {
    logger.info("Getting a bmodel");
    String dValue = restTemplate.getForObject(servicesConfiguration.getURIServiceD("d/error"), String.class);//will initiate new span
    return new BModel("this is service b", dValue);//will return to the first span & finish the trace
  }

  @GetMapping(path = "performance")//by calling the service, a trace & span is started
  public BModel getBModelPerformance() throws URISyntaxException {
    logger.info("Getting a bmodel");
    String dValue = restTemplate.getForObject(servicesConfiguration.getURIServiceD("d/performance"), String.class);//will initiate new span
    return new BModel("this is service b", dValue);//will return to the first span & finish the trace
  }
}
