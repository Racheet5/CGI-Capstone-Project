package StepDefinitions;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="Features/Amazon.feature",glue= {"StepDefinitions"},
monochrome=true,
plugin= {"pretty","html:target/HtmlReports.html"})
public class TestRunner {

}