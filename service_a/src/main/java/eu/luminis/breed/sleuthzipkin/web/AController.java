package eu.luminis.breed.sleuthzipkin.web;

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
@RequestMapping(path = "a")
public class AController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final RestTemplate restTemplate;
  private static final String BASE_URL = "http://localhost";
  private static final int PORT_B = 8082;
  private static final int PORT_C = 8083;

  @Autowired
  public AController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping//by calling the service, a trace & span is started
  public AModel getAModel() throws URISyntaxException {
    logger.info("Getting a amodel");
    BModel bModel = restTemplate.getForObject(new URIBuilder(BASE_URL).setPort(PORT_B).setPath("b").build(), BModel.class);//will initiate new span
    String cValue = restTemplate.getForObject(new URIBuilder(BASE_URL).setPort(PORT_C).setPath("c").build(), String.class);//will initiate new span
    return new AModel("this is service a", bModel.getB(), cValue, bModel.getD());//will return to the first span & finish the trace
  }

  /**
   * Use to demonstrate the tracing of an error in service d
   */
  @GetMapping(path = "error")
  public AModel getAmodelButError() throws URISyntaxException {
    logger.info("Getting a amodel");
    BModel bModel = restTemplate.getForObject(new URIBuilder(BASE_URL).setPort(PORT_B).setPath("b/error").build(), BModel.class);//will initiate new span
    String cValue = restTemplate.getForObject(new URIBuilder(BASE_URL).setPort(PORT_C).setPath("c").build(), String.class);//will initiate new span
    return new AModel("this is service a", bModel.getB(), cValue, bModel.getD());//will return to the first span & finish the trace
  }

  /**
   * If you do not peek into the other services, you can find which service is having a performance issue with the help of zipkin...
   */
  @GetMapping(path = "performance")
  public AModel getAModelWithPerformanceLag() throws URISyntaxException {
    logger.info("Getting a amodel");
    BModel bModel = restTemplate.getForObject(new URIBuilder(BASE_URL).setPort(PORT_B).setPath("b/performance").build(), BModel.class);//will initiate new span
    String cValue = restTemplate.getForObject(new URIBuilder(BASE_URL).setPort(PORT_C).setPath("c/performance").build(), String.class);//will initiate new span
    return new AModel("this is service a", bModel.getB(), cValue, bModel.getD());//will return to the first span & finish the trace
  }


}
