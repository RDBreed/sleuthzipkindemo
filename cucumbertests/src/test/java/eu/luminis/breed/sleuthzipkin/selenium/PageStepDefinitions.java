package eu.luminis.breed.sleuthzipkin.selenium;

import static org.junit.Assert.assertTrue;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONException;

public class PageStepDefinitions {

  private BreakfastPage breakfastPage;

  @Before
  public void before(){
    breakfastPage = new BreakfastPage(ChromeSetup.getChromeDriverWithLogging());
  }

  @After
  public void after(Scenario scenario) throws JSONException {
    scenario.write("With conversationId: " + breakfastPage.getConversationId());
    breakfastPage.close();
  }

  @Given("^a customer$")
  public void aCustomer() {
    breakfastPage.navigateToPage();
    //do nothing so far
  }

  @And("^a conversation header value of (.*)$")
  public void aConversationHeaderOfBlablabla(String headerValue) {
    System.out.println("This method is ignored for this type of test!");
  }

  @When("^a customer want a toasted bread$")
  public void aCustomerWantAToastedBread() throws InterruptedException {
    breakfastPage.toastBread();
    Thread.sleep(100);
  }

  @And("^a fried egg$")
  public void aFriedEgg() throws Throwable {
    breakfastPage.fryEgg();
    Thread.sleep(100);
  }

  @And("^some bacon$")
  public void someBacon() throws Throwable {
    breakfastPage.fryBacon();
    Thread.sleep(100);
  }

  @And("^a good cup of coffee$")
  public void aGoodCupOfCoffee() throws Throwable {
    breakfastPage.makeCoffee();
    Thread.sleep(100);
  }

  @Then("^he gets a wonderful breakfast$")
  public void heGetsAWonderfulBreakfast() throws Throwable {
    assertTrue(breakfastPage.hasBreakfast());
  }
}
