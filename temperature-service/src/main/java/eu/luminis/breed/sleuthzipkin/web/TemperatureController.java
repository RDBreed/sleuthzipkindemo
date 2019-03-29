package eu.luminis.breed.sleuthzipkin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "temperature")
public class TemperatureController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @GetMapping("toaster")//by calling the service, a trace & span is started
  public int getTemperatureToaster() {
    logger.info("Getting temperature of toaster");
    return 80;//will return to the first span & finish the trace
  }

  @GetMapping("stove")
  public int getTemperatureStove() {
    logger.info("Getting temperature of stove");
    return 90;
  }

  @GetMapping("coffeemachine")
  public int getTemperatureCoffeeMachine() {
    logger.info("Getting temperature of stove");
    return 95;
  }
}
