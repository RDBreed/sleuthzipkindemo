package eu.luminis.breed.sleuthzipkin;

import static eu.luminis.breed.sleuthzipkin.configuration.CustomTraceFilter.TAG_NAME;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.luminis.breed.sleuthzipkin.model.AModel;
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

public class ServiceAStepDefinition {

  private RestTemplate restTemplate;
  private AModel aModel;
  private String conversationId;
  private HttpHeaders httpHeaders = new HttpHeaders();

  @Before
  public void before() {
    restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new MyErrorHandler());
  }

  @After
  public void after(Scenario scenario) {
    scenario.write("With conversationId: " + conversationId);
  }

  @Given("^a customer$")
  public void aCustomer() {
    //do nothing so far
  }


  @When("^a customer calls service A$")
  public void aCustomerCallsServiceA() throws URISyntaxException, IOException {
    RequestEntity<String> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, new URI("http://localhost:8081/a"));
    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
    conversationId = responseEntity.getHeaders().get(TAG_NAME).get(0);
    aModel = new ObjectMapper().readValue(responseEntity.getBody(), AModel.class);
  }

  @Then("^a model is created$")
  public void aModelIsCreated() {
    assertNotNull(aModel);
  }

  @And("^a conversation header value of (.*)$")
  public void aConversationHeaderOfBlablabla(String headerValue) throws Throwable {
    httpHeaders.add(TAG_NAME, headerValue);
  }

  public class MyErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
      conversationId = response.getHeaders().get(TAG_NAME).get(0);
      fail("The response gave an statuscode " + response.getStatusCode() + " error with message: " + System.lineSeparator() + new BufferedReader(new InputStreamReader((response.getBody()))).lines()
          .collect(
              Collectors.joining("\n")));
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
      return !response.getStatusCode().is2xxSuccessful();
    }
  }
}
