package eu.luminis.breed.sleuthzipkin;

import static eu.luminis.breed.sleuthzipkin.configuration.CustomTraceFilter.TAG_NAME;
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

public class ServiceAApi {

  private HttpHeaders httpHeaders = new HttpHeaders();
  private RestTemplate restTemplate;
  private String conversationId;

  public ServiceAApi() {
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
    RequestEntity requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, new URI("http://localhost:8081/breakfast/toastedbread"));
    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
    conversationId = responseEntity.getHeaders().get(TAG_NAME).get(0);
    httpHeaders.add(TAG_NAME, conversationId);
    return responseEntity;
  }

  public ResponseEntity<String> getFriedEgg() throws URISyntaxException {
    RequestEntity requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, new URI("http://localhost:8081/breakfast/egg"));
    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
    conversationId = responseEntity.getHeaders().get(TAG_NAME).get(0);
    httpHeaders.add(TAG_NAME, conversationId);
    return responseEntity;
  }

  public ResponseEntity<String> getBacon() throws URISyntaxException {
    RequestEntity requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, new URI("http://localhost:8081/breakfast/bacon"));
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
