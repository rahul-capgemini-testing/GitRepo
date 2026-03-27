package StepDefinitions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StepDefinition {
	Response postResp;

	@Given("the baseURI {string}")
	public void the_base_uri(String baseUri) {
		RestAssured.baseURI = baseUri;
		System.out.println("Base uri set successfully");
	}

	@When("send the post request {string} with user details")
	public void send_the_post_request_with_user_details(String endPoint, DataTable dataTable) {
		List<Map<String, String>> maps = dataTable.asMaps();

		for (Map<String, String> map : maps) {
			String user = map.get("username");
			String pass = map.get("password");

			Map<String, String> credentials = new HashMap<String, String>();
			credentials.put("userName", user);
			credentials.put("password", pass);

			postResp = RestAssured.given().contentType(ContentType.JSON).body(credentials).when().post(endPoint);
			System.out.println("Created New USer Successfully");
		}

	}

	@Then("validate response status code with {int}")
	public void validate_response_status_code(int status) {
		postResp.then().statusCode(Matchers.not(201));
	}
}
