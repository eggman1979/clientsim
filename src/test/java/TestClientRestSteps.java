import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class TestClientRestSteps {
	
	private Client client;
	private User user;
	private String firstname;
	private String lastname;
	private String cpr;
	private final String lHost="http://localhost:8080";
	
	
	@Given("^(.*) (.*), with cpr number (.*) has an Account in Fast Money Bank$")
	public void setClient(String firstName, String lastName, String cpr) {
		firstname =firstName;
		lastname=lastName;
		this.cpr=cpr;
	
	}
	
	@When("^the (.*) wants to create an Account on DTU Pay using his smartphone$")
	public void createAccount(String clientName) {
	client = new DTUPayClientInterface(lHost).createAccount(firstname, lastname,cpr);
	}
	
	@Then("^DTU Pay should create an account for (.*)(.*)$")
	public void checkAccount(String firstname, String surname) {
		assertThat(client != null, is(true) );
		assertThat(this.firstname, is(client.firstName));
		assertThat(lastname, is(client.lastName));
		assertThat(cpr, is(client.cpr));
	}
}


