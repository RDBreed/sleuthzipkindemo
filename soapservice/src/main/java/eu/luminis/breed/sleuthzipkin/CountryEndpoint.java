package eu.luminis.breed.sleuthzipkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndpoint {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private static final String NAMESPACE_URI = "sleuthzipkin.breed.luminis.eu";

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
  @ResponsePayload
  public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
    logger.info("Get country");
    GetCountryResponse response = new GetCountryResponse();
    Country country = new Country();
    country.setCapital("Amsterdam");
    country.setCurrency(Currency.EUR);
    country.setName("Netherlands");
    country.setPopulation(17000000);
    response.setCountry(country);
    return response;
  }
}
