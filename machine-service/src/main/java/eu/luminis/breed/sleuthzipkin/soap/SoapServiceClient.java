package eu.luminis.breed.sleuthzipkin.soap;

import eu.luminis.breed.sleuthzipkin.Device;
import eu.luminis.breed.sleuthzipkin.GetEnergyRequest;
import eu.luminis.breed.sleuthzipkin.GetEnergyResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SoapServiceClient extends WebServiceGatewaySupport {


  public GetEnergyResponse getEnergyResponse(Device device) {
    GetEnergyRequest getEnergyRequest = new GetEnergyRequest();
    getEnergyRequest.setName(device);
    return (GetEnergyResponse) getWebServiceTemplate().marshalSendAndReceive(getEnergyRequest);
  }
}
