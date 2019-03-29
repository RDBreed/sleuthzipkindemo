package eu.luminis.breed.sleuthzipkin.selenium;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = "eu.luminis.breed.sleuthzipkin.selenium", format = {"pretty", "html:target/cucumber"}, features = "src/test/resources/")
public class BreakfastPageTest {
}