package eu.luminis.breed.sleuthzipkin;


import static eu.luminis.breed.sleuthzipkin.BreakFastServiceApi.TAG_NAME;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class GeneralStepDefinitions {

  private BreakFastServiceApi serviceAApi;

  public GeneralStepDefinitions(BreakFastServiceApi serviceAApi) {
    this.serviceAApi = serviceAApi;
  }

  @After
  public void after(Scenario scenario) {
    scenario.write("With conversationId: " + serviceAApi.getConversationId());
  }

  @Given("^a customer$")
  public void aCustomer() {
    //do nothing so far
  }

  @And("^a conversation header value of (.*)$")
  public void aConversationHeaderOfBlablabla(String headerValue) {
    serviceAApi.addHeader(TAG_NAME, headerValue);
  }
}
