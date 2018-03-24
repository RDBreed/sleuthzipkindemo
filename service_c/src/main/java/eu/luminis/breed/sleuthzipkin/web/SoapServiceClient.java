package eu.luminis.breed.sleuthzipkin.web;

import eu.luminis.breed.sleuthzipkin.GetCountryRequest;
import eu.luminis.breed.sleuthzipkin.GetCountryResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SoapServiceClient extends WebServiceGatewaySupport {


  public GetCountryResponse getCountryResponse() {
    GetCountryRequest getCountryRequest = new GetCountryRequest();
    return (GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive(getCountryRequest);
  }
}
