package eu.luminis.breed.sleuthzipkin;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.luminis.breed.sleuthzipkin.model.FriedBacon;
import eu.luminis.breed.sleuthzipkin.model.FriedEgg;
import eu.luminis.breed.sleuthzipkin.model.ToastedBread;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.http.ResponseEntity;

public class ServiceABreakfastStepDefinition {

  private ToastedBread toastedBread;
  private FriedEgg friedEgg;
  private FriedBacon friedBacon;
  private ServiceAApi serviceAApi;

  public ServiceABreakfastStepDefinition(ServiceAApi serviceAApi) {
    this.serviceAApi = serviceAApi;
  }


  @When("^a customer want a toasted bread$")
  public void aCustomerWantAToastedBread() throws URISyntaxException, IOException {
    ResponseEntity<String> responseEntity = serviceAApi.getToastedBread();
    toastedBread = new ObjectMapper().readValue(responseEntity.getBody(), ToastedBread.class);
  }

  @And("^a fried egg$")
  public void aFriedEgg() throws Throwable {
    ResponseEntity<String> responseEntity = serviceAApi.getFriedEgg();
    friedEgg = new ObjectMapper().readValue(responseEntity.getBody(), FriedEgg.class);
  }

  @And("^some bacon$")
  public void someBacon() throws Throwable {
    ResponseEntity<String> responseEntity = serviceAApi.getBacon();
    friedBacon = new ObjectMapper().readValue(responseEntity.getBody(), FriedBacon.class);
  }

  @Then("^he gets a wonderful breakfast$")
  public void heGetsAWonderfulBreakfast() throws Throwable {
    assertNotNull(toastedBread);
    assertNotNull(friedEgg);
    assertNotNull(friedBacon);
  }
}
