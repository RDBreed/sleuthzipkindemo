package eu.luminis.breed.sleuthzipkin.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice//Just to prevent the default error page from loading, as this starts another trace (which is logical, as it will be another call/event).
public class GlobalExceptionHandler {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Error getError(Exception exception){
    logger.error("Something went wrong", exception);
    return new Error(exception.getMessage());
  }
}
