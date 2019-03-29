package eu.luminis.breed.sleuthzipkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EnergyEndpoint {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private static final String NAMESPACE_URI = "sleuthzipkin.breed.luminis.eu";

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEnergyRequest")
  @ResponsePayload
  public GetEnergyResponse getEnergyResponse(@RequestPayload GetEnergyRequest request) {
    logger.info("Get energy");
    GetEnergyResponse response = new GetEnergyResponse();
    Energy energy = new Energy();
    if (request.getName() == Device.TOASTER) {
      energy.setValue(5);
      energy.setUnit(Unit.K_WH);
    } else if (request.getName() == Device.STOVE) {
      energy.setValue(1);
      energy.setUnit(Unit.M_3);
    } else if (request.getName() == Device.COFFEEMACHINE) {
      energy.setValue(75);
      energy.setUnit(Unit.K_WH);
    }
    response.setEnergy(energy);
    return response;
  }
}
