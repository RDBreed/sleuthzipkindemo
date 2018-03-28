package eu.luminis.breed.sleuthzipkin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "d")
public class DController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @GetMapping//by calling the service, a trace & span is started
  public String getDModel() {
    logger.info("Getting a dmodel");
    return "this is service d";//will return to the first span & finish the trace
  }

  @GetMapping(path = "error")//by calling the service, a trace & span is started
  public String getDModelButError() {
    logger.info("Getting a dmodel");
    throw new IllegalArgumentException("this went wrong");
  }

  @GetMapping(path = "performance")//by calling the service, a trace & span is started
  public String getDModelPerformance() {
    logger.info("Getting a dmodel");
    return "this is service d";//will return to the first span & finish the trace
  }

}
