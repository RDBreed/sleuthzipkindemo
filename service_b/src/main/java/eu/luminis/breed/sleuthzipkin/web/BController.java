package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.configuration.BModel;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;
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
  private static final String BASE_URL = "http://localhost";
  private static final int PORT_D = 8084;

  @Autowired
  public BController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping//by calling the service, a trace & span is started
  public BModel getBModel() throws URISyntaxException {
    logger.info("Getting a bmodel");
    String dValue = restTemplate.getForObject(new URIBuilder(BASE_URL).setPort(PORT_D).setPath("d").build(), String.class);//will initiate new span
    return new BModel("this is service b", dValue);//will return to the first span & finish the trace
  }

  @GetMapping(path = "error")//by calling the service, a trace & span is started
  public BModel getBModelButError() throws URISyntaxException {
    logger.info("Getting a bmodel");
    String dValue = restTemplate.getForObject(new URIBuilder(BASE_URL).setPort(PORT_D).setPath("d/error").build(), String.class);//will initiate new span
    return new BModel("this is service b", dValue);//will return to the first span & finish the trace
  }

  @GetMapping(path = "performance")//by calling the service, a trace & span is started
  public BModel getBModelPerformance() throws URISyntaxException {
    logger.info("Getting a bmodel");
    String dValue = restTemplate.getForObject(new URIBuilder(BASE_URL).setPort(PORT_D).setPath("d/performance").build(), String.class);//will initiate new span
    return new BModel("this is service b", dValue);//will return to the first span & finish the trace
  }
}
