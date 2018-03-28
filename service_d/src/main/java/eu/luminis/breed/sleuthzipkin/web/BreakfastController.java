package eu.luminis.breed.sleuthzipkin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "breakfast")
public class BreakfastController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private int count = 0;

  @GetMapping("totaltoasted")//by calling the service, a trace & span is started
  public int getToastedBread() {
    logger.info("Getting total of counts");
    return ++count;//will return to the first span & finish the trace
  }

  @GetMapping("baconorigin")
  public String getOrigin() {
    logger.info("Getting origin of bacon");
    return "Dutch organic pig";
  }
}
