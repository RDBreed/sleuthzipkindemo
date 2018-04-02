package eu.luminis.breed.sleuthzipkin;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class BreakFastServiceApi {

  public static final String TAG_NAME = "X-B3-CONVID";

  private HttpHeaders httpHeaders = new HttpHeaders();
  private RestTemplate restTemplate;
  private String conversationId;
  private String breakFastEndpoint = "http://localhost:8081/breakfast";
  ;

  public BreakFastServiceApi() {
    restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new MyErrorHandler());
  }

  public void addHeader(String tagName, String headerValue) {
    httpHeaders.add(tagName, headerValue);
  }

  public String getConversationId() {
    return conversationId;
  }

  public ResponseEntity<String> getToastedBread() throws URISyntaxException {
    return getResponse(breakFastEndpoint + "/toastedbread");
  }

  public ResponseEntity<String> getFriedEgg() throws URISyntaxException {
    return getResponse(breakFastEndpoint + "/egg");
  }

  public ResponseEntity<String> getBacon() throws URISyntaxException {
    return getResponse(breakFastEndpoint + "/bacon");
  }

  public ResponseEntity<String> getCoffee() throws URISyntaxException {
    return getResponse(breakFastEndpoint + "/coffee");
  }

  private ResponseEntity<String> getResponse(String s) throws URISyntaxException {
    RequestEntity requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, new URI(s));
    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
    conversationId = responseEntity.getHeaders().get(TAG_NAME).get(0);
    httpHeaders.add(TAG_NAME, conversationId);
    return responseEntity;
  }

  public class MyErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
      conversationId = response.getHeaders().get(TAG_NAME).get(0);
      fail("The response gave an statuscode " + response.getStatusCode() + " error with message: " + System.lineSeparator() + new BufferedReader(new InputStreamReader((response.getBody()))).lines()
          .collect(Collectors.joining("\n")));
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
      return !response.getStatusCode().is2xxSuccessful();
    }
  }
}
