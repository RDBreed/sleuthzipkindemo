package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration;
import eu.luminis.breed.sleuthzipkin.model.AModel;
import eu.luminis.breed.sleuthzipkin.model.BModel;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
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


  //example of async process
  @GetMapping(path="multithreads")//by calling the service, a trace & span is started
  public AModel getAModelByMultipleThreads() throws URISyntaxException, ExecutionException, InterruptedException {
    logger.info("Getting a amodel");

    Future<BModel> bModelFuture = performAsync(() -> {
      logger.info("getting model b");
      return restTemplate.getForObject(servicesConfiguration.getURIServiceB("b"), BModel.class);
    });
    Future<String> cModelFuture = performAsync(() -> {
      logger.info("getting model c");
      return restTemplate.getForObject(servicesConfiguration.getURIServiceC("c"), String.class);
    });

    BModel bModel = bModelFuture.get();
    return new AModel("this is service a", bModel.getB(), cModelFuture.get(), bModel.getD());//will return to the first span & finish the trace
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

  private <T> Future<T> performAsync(Supplier<T> function){
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Callable<T> callable = function::get;
    Future<T> future = executor.submit(callable);
    executor.shutdown();
    return future;
  }


}
